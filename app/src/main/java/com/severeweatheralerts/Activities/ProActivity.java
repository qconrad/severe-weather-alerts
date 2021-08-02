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
  }

  public void purchaseClick(View view) {
    if (billingClient.isReady()) {
      Toast.makeText(this, "Attempting query", Toast.LENGTH_SHORT).show();
      SkuDetailsParams params = SkuDetailsParams.newBuilder()
              .setSkusList(Arrays.asList("pro_yearly"))
              .setType(BillingClient.SkuType.SUBS)
              .build();
      billingClient.querySkuDetailsAsync(params, (billingResult, list) -> {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
          BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                  .setSkuDetails(list.get(0))
                  .build();
          int responseCode = billingClient.launchBillingFlow(this, billingFlowParams).getResponseCode();
          // TODO: Handle the result.
        }
      });
    } else
      Toast.makeText(this, "Sorry, billing services is not available right now. Try again later.", Toast.LENGTH_SHORT).show();
  }
}