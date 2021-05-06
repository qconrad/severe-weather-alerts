package com.severeweatheralerts.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.severeweatheralerts.Location.BackgroundLocation;
import com.severeweatheralerts.Location.ConditionalDefaultLocationSync;
import com.severeweatheralerts.Location.GPSLocation;
import com.severeweatheralerts.Location.LastKnownLocation;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.PermissionManager;
import com.severeweatheralerts.R;

import java.util.Date;
import java.util.concurrent.locks.Condition;

public class GettingLocationActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_loading);
    if (!PermissionManager.hasCoarseLocation(this))
      startActivity(new Intent(GettingLocationActivity.this, FirstRunActivity.class));
    else {
      LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
      checkIfLocationsServicesAreEnabled(lm);
      setLoadingStatus();
      setProgressbarColor();
      populateLocations();
    }
  }

  private void checkIfLocationsServicesAreEnabled(LocationManager lm) {
    if (!lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
      if (LocationsDao.getInstance(this).hasLocations()) {
        LocationsDao.getInstance(this).setName(0, "Last Known Location");
        fetchAlerts();
      } else {
        LocationsDao.getInstance(this).setName(0, "No Location");
        displayError("Location services disabled");
      }
    }
  }

  private void displayError(String message) {
    Intent intent = new Intent(GettingLocationActivity.this, ErrorActivity.class);
    intent.putExtra("errorMessage", message);
    startActivity(intent);
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }

  private void setLoadingStatus() {
    TextView tv = findViewById(R.id.loading_status);
    tv.setText(R.string.location_fetch_text);
  }

  private void setProgressbarColor() {
    ProgressBar pb = findViewById(R.id.fetch_progress);
    pb.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
  }

  GPSLocation gps;
  private void populateLocations() {
    android.location.Location lastKnown = new LastKnownLocation(this).getLocation();
    if (notNull(lastKnown) && notOutdated(lastKnown)) setDefaultLocation(lastKnown);
    else useGPS();
  }

  private boolean notOutdated(android.location.Location lastKnown) {
    return lastKnown.getTime() > new Date().getTime() - getLastKnownExpireTimeMS();
  }

  private boolean notNull(android.location.Location lastKnown) {
    return lastKnown != null;
  }

  private int getLastKnownExpireTimeMS() {
    return 1000*60*20; // 20 minutes
  }

  private void useGPS() {
    gps = new GPSLocation(this, location -> {
      if (accurateEnough(location)) useLocation(location);
    }).startUpdates();
  }

  private void useLocation(android.location.Location location) {
    gps.stopUpdates();
    setDefaultLocation(location);
  }

  protected boolean accurateEnough(android.location.Location location) {
    return location.getAccuracy() < 100;
  }

  private void setDefaultLocation(android.location.Location location) {
    LocationsDao.getInstance(this).setName(0, "Current Location");
    new BackgroundLocation(this).start();
    new ConditionalDefaultLocationSync(this, location.getLatitude(), location.getLongitude()).sync();
    fetchAlerts();
  }

  private void fetchAlerts() {
    Intent alertListIntent = new Intent(GettingLocationActivity.this, GettingLatestDataActivity.class);
    startActivity(alertListIntent);
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }

  @Override
  public void onBackPressed() {
    finishAffinity();
  }
}