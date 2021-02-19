package com.severeweatheralerts.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.severeweatheralerts.Location.GPSLocation;
import com.severeweatheralerts.Location.LastKnownLocation;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.Networking.LocationPopulaters.AllNWSPopulater;
import com.severeweatheralerts.Networking.LocationPopulaters.PopulateCallback;
import com.severeweatheralerts.PermissionManager;
import com.severeweatheralerts.R;

import java.util.Date;

public class GettingLocationActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fetching_alert_data);
    checkPermissions();
    populateLocations();
  }

  GPSLocation gps;
  private void populateLocations() {
    Location deviceLoc = new Location();
    android.location.Location lastKnown = new LastKnownLocation(this).getLocation();
    if (notNull(lastKnown) && notOutdated(lastKnown)) setDeviceLocation(deviceLoc, lastKnown);
    else useGPS(deviceLoc);
  }

  private boolean notOutdated(android.location.Location lastKnown) {
    return lastKnown.getTime() > new Date().getTime() - getLastKnownExpireTimeMS();
  }

  private boolean notNull(android.location.Location lastKnown) {
    return lastKnown != null;
  }

  private int getLastKnownExpireTimeMS() {
    return 1000*60*45; // 45 minutes
  }

  private void useGPS(Location deviceLoc) {
    gps = new GPSLocation(this, location -> {
      if (accurateEnough(location)) useLocation(deviceLoc, location);
    }).startUpdates();
  }

  private void useLocation(Location deviceLoc, android.location.Location location) {
    gps.stopUpdates();
    setDeviceLocation(deviceLoc, location);
  }

  protected boolean accurateEnough(android.location.Location location) {
    return location.getAccuracy() < 100;
  }

  private void checkPermissions() {
    if (!PermissionManager.hasLocationPermissions(this)) PermissionManager.requestLocationPermissions(this);
  }

  private void setDeviceLocation(Location deviceLoc, android.location.Location location) {
    adaptLocation(deviceLoc, location);
    LocationsDao.addLocation(deviceLoc);
    getAlerts();
  }

  private void getAlerts() {
    new AllNWSPopulater(LocationsDao.getLocation(0), this).populate(new PopulateCallback() {
      @Override
      public void complete() {
        displayAlerts();
      }

      @Override
      public void error(String message) {
        Toast.makeText(GettingLocationActivity.this, message, Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void displayAlerts() {
    Intent alertListIntent = new Intent(GettingLocationActivity.this, AlertListActivity.class);
    startActivity(alertListIntent);
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }

  private void adaptLocation(Location loc, android.location.Location deviceLoc) {
    loc.setLatitude(deviceLoc.getLatitude());
    loc.setLongitude(deviceLoc.getLongitude());
  }
}