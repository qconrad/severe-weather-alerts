package com.severeweatheralerts.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.GeofenceManager;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.Networking.LocationPopulaters.FromLocationPointPopulater;
import com.severeweatheralerts.Networking.LocationPopulaters.PopulateCallback;
import com.severeweatheralerts.R;
import com.severeweatheralerts.UserSync.UserSyncWorkScheduler;

import java.util.ArrayList;

public class FetchingAlertDataActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_loading);
    getAlerts();
    setProgressbarColor();
    new UserSyncWorkScheduler(this).oneTimeSync();
    GCSCoordinate coordinate = LocationsDao.getCoordinate(0);
    new GeofenceManager(this).setGeofence(coordinate.getLat(), coordinate.getLong(), 400);
  }

  private void setProgressbarColor() {
    ProgressBar pb = findViewById(R.id.fetch_progress);
    pb.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
  }

  private void getAlerts() {
    new FromLocationPointPopulater(LocationsDao.getCoordinate(0), this).populate(new PopulateCallback() {
      @Override
      public void complete(ArrayList<Alert> alerts) {
        LocationsDao.setAlerts(0, alerts);
        displayAlerts();
      }

      @Override
      public void error(String message) {
        Toast.makeText(FetchingAlertDataActivity.this, message, Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void displayAlerts() {
    startActivity(new Intent(FetchingAlertDataActivity.this, AlertListActivity.class));
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }

  @Override
  public void onBackPressed() {
    finishAffinity();
  }
}