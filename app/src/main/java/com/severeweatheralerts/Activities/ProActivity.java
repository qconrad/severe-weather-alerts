package com.severeweatheralerts.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.SkuDetailsParams;
import com.severeweatheralerts.BillingClientSetup;
import com.severeweatheralerts.R;

import java.util.Arrays;

public class ProActivity extends AppCompatActivity {
  private BillingClient billingClient;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pro);
    billingClient = BillingClientSetup.getInstance(this, null);
  }

  public void monthlyClick(View view) {
    purchase("pro_monthly");
  }

  public void yearlyClick(View view) {
    purchase("pro_yearly");
  }

  private void purchase(String sku) {
    if (billingClient.isReady()) {
      SkuDetailsParams params = SkuDetailsParams.newBuilder()
              .setSkusList(Arrays.asList(sku))
              .setType(BillingClient.SkuType.SUBS)
              .build();
      billingClient.querySkuDetailsAsync(params, (billingResult, list) -> {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null && list.size() > 0) {
          BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                  .setSkuDetails(list.get(0))
                  .build();
          billingClient.launchBillingFlow(this, billingFlowParams);
        }
      });
    } else
      Toast.makeText(this, "Sorry, billing services is not available right now. Try again later.", Toast.LENGTH_SHORT).show();
  }
}