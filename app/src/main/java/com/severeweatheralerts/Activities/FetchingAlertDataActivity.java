package com.severeweatheralerts.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.severeweatheralerts.Location.GPSLocation;
import com.severeweatheralerts.Location.LastKnownLocation;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.Networking.LocationPopulaters.AllNWSPopulater;
import com.severeweatheralerts.Networking.LocationPopulaters.FromLocationPointPopulater;
import com.severeweatheralerts.Networking.LocationPopulaters.PopulateCallback;
import com.severeweatheralerts.PermissionManager;
import com.severeweatheralerts.R;

import java.util.Date;

public class FetchingAlertDataActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fetching_alert_data);
    getAlerts();
  }


  private void getAlerts() {
    new FromLocationPointPopulater(LocationsDao.getLocation(0), this).populate(new PopulateCallback() {
      @Override
      public void complete() {
        displayAlerts();
      }

      @Override
      public void error(String message) {
        Toast.makeText(FetchingAlertDataActivity.this, message, Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void displayAlerts() {
    Intent alertListIntent = new Intent(FetchingAlertDataActivity.this, AlertListActivity.class);
    startActivity(alertListIntent);
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }
}