package com.severeweatheralerts.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.severeweatheralerts.R;
import com.severeweatheralerts.Billing.SubscriptionManager;

public class ProActivity extends AppCompatActivity {
  private SubscriptionManager subscriptionManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pro);
    subscriptionManager = new SubscriptionManager(this);
  }

  public void monthlyClick(View view) {
    subscriptionManager.purchaseMonthly();
  }

  public void yearlyClick(View view) {
    subscriptionManager.purchaseYearly();
  }
}