package com.severeweatheralerts.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.severeweatheralerts.R;

public class PurchaseActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_purchase);
  }

  @Override
  public void onBackPressed() {
    startActivity(new Intent(this, AlertListActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
  }

  public void goToExtra(View view) {
    startActivity(new Intent(this, ManageLocationsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
  }

  public void returnToMain(View view) {
    finish();
  }
}