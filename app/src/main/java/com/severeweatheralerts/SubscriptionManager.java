package com.severeweatheralerts;

import static com.severeweatheralerts.FileDB.getLocationsDao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetailsParams;
import com.severeweatheralerts.Activities.PurchaseActivity;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.UserSync.UserSyncWorkScheduler;

import java.util.Arrays;
import java.util.List;

public class SubscriptionManager {
  private final BillingClient billingClient;
  private final LocationsDao locationsDao;
  private final Context context;
  private boolean isPro = false;

  public SubscriptionManager(Context context) {
    this.context = context;
    billingClient = BillingClientSingleton.getInstance(context, this::handlePurchases);
    this.locationsDao = getLocationsDao(context);
  }

  public void purchaseMonthly() {
    purchaseSku("pro_monthly");
  }

  public void purchaseYearly() {
    purchaseSku("pro_yearly");
  }

  public void startBillingConnection() {
    billingClient.startConnection(new BillingClientStateListener() {
      @Override
      public void onBillingServiceDisconnected() {
        startBillingConnection();
      }

      @Override
      public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
          billingClient.queryPurchasesAsync(BillingClient.SkuType.SUBS, (queryPurchasesResult, list) -> handlePurchases(queryPurchasesResult, list));
        } else {
          Toast.makeText(context, billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  public void checkPurchases() {
    if (!billingClient.isReady())
      return;

    billingClient.queryPurchasesAsync(BillingClient.SkuType.SUBS, this::handlePurchases);
  }

  private void handlePurchases(BillingResult billingResult, List<Purchase> purchases) {
    // Check if billing client return success
    if (billingResult.getResponseCode() != BillingClient.BillingResponseCode.OK)
      return;

    // Assume not pro then loop through purchases and set isPro if a purchase is found
    isPro = false;
    for (Purchase purchase : purchases)
      handlePurchase(purchase);

    // Store pro status and revoke privileges if necessary
    PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("is_pro", isPro).apply();
    revokePrivilegesIfNecessary();
  }

  /* Removes extra locations and syncs to backend
     TODO: It is quite rude to delete locations. A user could be switching plans or decide to
           resubscribe later. In the future, their locations should be preserved should they
           become pro again for any reason. */
  private void revokePrivilegesIfNecessary() {
    if (!isPro && locationsDao.hasExtraLocations()) {
      locationsDao.deleteExtraLocations();
      new UserSyncWorkScheduler(context).oneTimeSync();
    }
  }

  private void handlePurchase(Purchase purchase) {
    // Ignore purchases that do not have a purchased state
    if (purchase.getPurchaseState() != Purchase.PurchaseState.PURCHASED)
      return;

    // If the purchase has the correct state, set the pro subscription and acknowledge the purchase
    isPro = true;
    if (!purchase.isAcknowledged())
      acknowledgePurchase(purchase);
  }

  // Inform the billing service that access has been granted and start the activity that thanks the user for their support
  private void acknowledgePurchase(Purchase purchase) {
    billingClient.acknowledgePurchase(getAcknowledgePurchaseParams(purchase), billingResult -> startPurchaseActivity());
  }

  private AcknowledgePurchaseParams getAcknowledgePurchaseParams(Purchase purchase) {
    return AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchase.getPurchaseToken())
            .build();
  }

  private void startPurchaseActivity() {
    context.startActivity(new Intent(context, PurchaseActivity.class));
  }

  private void purchaseSku(String sku) {
    if (!billingClient.isReady()) {
      Toast.makeText(context, "Sorry, billing services are not available right now. Try again later.", Toast.LENGTH_SHORT).show();
      return;
    }

    SkuDetailsParams params = SkuDetailsParams.newBuilder()
            .setSkusList(Arrays.asList(sku))
            .setType(BillingClient.SkuType.SUBS)
            .build();
    billingClient.querySkuDetailsAsync(params, (billingResult, list) -> {
      if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null && list.size() > 0) {
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(list.get(0))
                .build();
        billingClient.launchBillingFlow((Activity) context, billingFlowParams);
      }
    });
  }
}
