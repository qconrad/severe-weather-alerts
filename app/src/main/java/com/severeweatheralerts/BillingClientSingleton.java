package com.severeweatheralerts;

import android.content.Context;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.PurchasesUpdatedListener;

public class BillingClientSingleton {
  private static BillingClient instance;

  public static BillingClient getInstance(Context context, PurchasesUpdatedListener listener) {
    if (instance == null) setupBillingClient(context, listener);
    return instance;
  }

  private static void setupBillingClient(Context context, PurchasesUpdatedListener listener) {
    instance = BillingClient.newBuilder(context)
            .enablePendingPurchases()
            .setListener(listener)
            .build();
  }
}
