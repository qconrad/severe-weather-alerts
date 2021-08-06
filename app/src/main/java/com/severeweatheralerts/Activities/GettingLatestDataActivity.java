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

public class GettingLatestDataActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_loading);
    checkForLocations();
    int locationIndex = getIntent().getIntExtra("locationIndex", 0);
    getAlerts(locationIndex);
    setProgressbarColor();
  }

  private void checkForLocations() {
    if (LocationsDao.getInstance(this).hasLocations()) return;
    startActivity(new Intent(GettingLatestDataActivity.this, FirstRunActivity.class));
  }

  private void setProgressbarColor() {
    ProgressBar pb = findViewById(R.id.fetch_progress);
    pb.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
  }

  private void getAlerts(int locationIndex) {
    LocationsDao locationsDao = LocationsDao.getInstance(this);
    new FromLocationPointPopulater(locationsDao.getCoordinate(locationIndex), this).populate(new PopulateCallback() {
      @Override
      public void complete(ArrayList<Alert> alerts) {
        locationsDao.setAlerts(locationIndex, alerts);
        displayAlerts(locationIndex);
      }

      @Override
      public void error(String message) {
        displayError(message);
      }
    });
  }

  private void displayAlerts(int locationIndex) {
    Intent intent = new Intent(GettingLatestDataActivity.this, AlertListActivity.class);;
    intent.putExtra("locationIndex", locationIndex);
    startActivity(intent);
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }

  private void displayError(String message) {
    Intent intent = new Intent(GettingLatestDataActivity.this, ErrorActivity.class);
    intent.putExtra("errorMessage", message);
    startActivity(intent);
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }

  @Override
  public void onBackPressed() {
    finishAffinity();
  }
}