package com.severeweatheralerts.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.severeweatheralerts.Location.GPSLocation;
import com.severeweatheralerts.Location.LastKnownLocation;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.PermissionManager;
import com.severeweatheralerts.R;

import java.util.Date;

public class GettingLocationActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_loading);
    setLoadingStatus();
    setProgressbarColor();
    checkPermissions();
    populateLocations();
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
    Location deviceLocation = new Location("Current Location");
    android.location.Location lastKnown = new LastKnownLocation(this).getLocation();
    if (notNull(lastKnown) && notOutdated(lastKnown)) setDefaultLocation(deviceLocation, lastKnown);
    else useGPS(deviceLocation);
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

  private void useGPS(Location deviceLoc) {
    gps = new GPSLocation(this, location -> {
      if (accurateEnough(location)) useLocation(deviceLoc, location);
    }).startUpdates();
  }

  private void useLocation(Location deviceLoc, android.location.Location location) {
    gps.stopUpdates();
    setDefaultLocation(deviceLoc, location);
  }

  protected boolean accurateEnough(android.location.Location location) {
    return location.getAccuracy() < 100;
  }

  private void checkPermissions() {
    if (!PermissionManager.hasLocationPermissions(this)) PermissionManager.requestLocationPermissions(this);
  }

  private void setDefaultLocation(Location defaultLocation, android.location.Location location) {
    adaptLocation(defaultLocation, location);
    LocationsDao.setDefaultLocation(defaultLocation);
    fetchAlerts();
  }

  private void fetchAlerts() {
    Intent alertListIntent = new Intent(GettingLocationActivity.this, FetchingAlertDataActivity.class);
    startActivity(alertListIntent);
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }

  private void adaptLocation(Location loc, android.location.Location deviceLoc) {
    loc.setLatitude(deviceLoc.getLatitude());
    loc.setLongitude(deviceLoc.getLongitude());
  }

  @Override
  public void onBackPressed() {
    finishAffinity();
  }
}