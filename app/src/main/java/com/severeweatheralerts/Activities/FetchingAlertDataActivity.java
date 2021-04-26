package com.severeweatheralerts.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.Networking.LocationPopulaters.FromLocationPointPopulater;
import com.severeweatheralerts.Networking.LocationPopulaters.PopulateCallback;
import com.severeweatheralerts.R;

import java.util.ArrayList;

public class FetchingAlertDataActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_loading);
    getAlerts();
    setProgressbarColor();
  }

  private void setProgressbarColor() {
    ProgressBar pb = findViewById(R.id.fetch_progress);
    pb.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
  }

  private void getAlerts() {
    LocationsDao locationsDao = LocationsDao.getInstance(this);
    new FromLocationPointPopulater(locationsDao.getCoordinate(0), this).populate(new PopulateCallback() {
      @Override
      public void complete(ArrayList<Alert> alerts) {
        locationsDao.setAlerts(0, alerts);
        displayAlerts();
      }

      @Override
      public void error(String message) {
        displayError(message);
      }
    });
  }

  private void displayAlerts() {
    startActivity(new Intent(FetchingAlertDataActivity.this, AlertListActivity.class));
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }

  private void displayError(String message) {
    Intent intent = new Intent(FetchingAlertDataActivity.this, ErrorActivity.class);
    intent.putExtra("errorMessage", message);
    startActivity(intent);
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }

  @Override
  public void onBackPressed() {
    finishAffinity();
  }
}