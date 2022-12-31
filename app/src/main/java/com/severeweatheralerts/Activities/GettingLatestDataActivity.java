package com.severeweatheralerts.Activities;

import static com.severeweatheralerts.FileDB.getLocationsDao;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Networking.LocationPopulaters.FromLocationPointPopulater;
import com.severeweatheralerts.Networking.LocationPopulaters.PopulateCallback;
import com.severeweatheralerts.NewAlerts;
import com.severeweatheralerts.R;

import java.util.ArrayList;

public class GettingLatestDataActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_loading);

    int locationIndex = getIntent().getIntExtra("locationIndex", 0);
    Location location = getLocationsDao(this).getLocation(locationIndex);

    if (location.coordinateSet()) fetchAlertData(location, locationIndex);
    else startActivity(new Intent(GettingLatestDataActivity.this, FirstRunActivity.class));
  }

  private void fetchAlertData(Location location, int locationIndex) {
    getAlerts(location, locationIndex);
    setProgressbarColor();
  }

  private void setProgressbarColor() {
    ProgressBar pb = findViewById(R.id.fetch_progress);
    pb.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
  }

  private void getAlerts(Location location, int locationIndex) {
    new FromLocationPointPopulater(location.getCoordinate(), this).populate(new PopulateCallback() {
      @Override
      public void complete(ArrayList<Alert> alerts) {
        location.setAlerts(alerts);
        NewAlerts.acknowledged();
        displayAlerts(locationIndex);
      }

      @Override
      public void error(String message) {
        displayError(message);
      }
    });
  }

  private void displayAlerts(int locationIndex) {
    startActivity(new Intent(GettingLatestDataActivity.this, AlertListActivity.class)
            .putExtra(("locationIndex"), locationIndex));
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }

  private void displayError(String message) {
    startActivity(new Intent(GettingLatestDataActivity.this, ErrorActivity.class)
            .putExtra("errorMessage", message));
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }

  @Override
  public void onBackPressed() {
    finishAffinity();
  }
}