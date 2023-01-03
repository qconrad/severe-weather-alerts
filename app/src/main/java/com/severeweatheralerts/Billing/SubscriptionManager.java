package com.severeweatheralerts.Billing;

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
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import com.google.common.collect.ImmutableList;
import com.severeweatheralerts.Activities.PurchaseActivity;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.UserSync.UserSyncWorkScheduler;

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
           billingClient.queryPurchasesAsync(getQueryPurchaseParams(), (queryPurchasesResult, list) -> handlePurchases(queryPurchasesResult, list));
        }
      }
    });
  }

  public void checkPurchases() {
    if (!billingClient.isReady())
      return;

    billingClient.queryPurchasesAsync(getQueryPurchaseParams(), this::handlePurchases);
  }

  private QueryPurchasesParams getQueryPurchaseParams() {
    return QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build();
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
    updateLocationsIfNecessary();
  }

  private void updateLocationsIfNecessary() {
    if (!isPro && locationsDao.hasExtraLocations()) {
      locationsDao.storeAwayExtraLocations();
      new UserSyncWorkScheduler(context).oneTimeSync();
    } else if (isPro && locationsDao.hasStoredAwayLocations()) {
      locationsDao.restoreStoredAwayLocations();
      new UserSyncWorkScheduler(context).oneTimeSync();
    }
  }

  private void handlePurchase(Purchase purchase) {
    // Ignore purchases that do not have a purchased state
    if (purchase.getPurchaseState() != Purchase.PurchaseState.PURCHASED)
      return;

    // If the purchase has the correct state, set the pro subscription and acknowledge the purchase
    isPro = false;
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

    billingClient.queryProductDetailsAsync(getQueryProductDetailsParams(sku), this::launchBillingFlow);
  }

  private void launchBillingFlow(BillingResult billingResult, List<ProductDetails> list) {
    if (billingResult.getResponseCode() != BillingClient.BillingResponseCode.OK) {
      Toast.makeText(context, "Error getting available products", Toast.LENGTH_SHORT).show();
      return;
    }

    BillingResult launchBillingFlowResult = billingClient.launchBillingFlow((Activity) context, getBillingFlowParams(list));
    if (launchBillingFlowResult.getResponseCode() != BillingClient.BillingResponseCode.OK) {
      Toast.makeText(context, "Error launching billing flow", Toast.LENGTH_SHORT).show();
    }
  }

  private BillingFlowParams getBillingFlowParams(List<ProductDetails> list) {
    return BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(getProductDetailsParams(list))
            .build();
  }

  private ImmutableList<BillingFlowParams.ProductDetailsParams> getProductDetailsParams(List<ProductDetails> list) {
    return ImmutableList.of(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                    .setProductDetails(list.get(0))
                    .setOfferToken(getOfferToken(list.get(0)))
                    .build()
    );
  }

  private String getOfferToken(ProductDetails productDetails) {
    List<ProductDetails.SubscriptionOfferDetails> subscriptionOfferDetails = productDetails.getSubscriptionOfferDetails();
    return subscriptionOfferDetails.get(0).getOfferToken();
  }

  private QueryProductDetailsParams getQueryProductDetailsParams(String sku) {
    return QueryProductDetailsParams.newBuilder()
            .setProductList(
                    ImmutableList.of(
                            QueryProductDetailsParams.Product.newBuilder()
                                    .setProductId(sku)
                                    .setProductType(BillingClient.ProductType.SUBS)
                                    .build()))
            .build();
  }
}
