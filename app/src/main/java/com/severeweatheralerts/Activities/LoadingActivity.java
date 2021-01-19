package com.severeweatheralerts.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.severeweatheralerts.Location.GPSLocation;
import com.severeweatheralerts.Location.LastKnownLocation;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.PermissionManager;
import com.severeweatheralerts.R;

public class LoadingActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_loading);
    checkPermissions();
    populateLocations();
  }

  GPSLocation gps;
  private void populateLocations() {
    Location deviceLoc = new Location();
    android.location.Location lastKnown = new LastKnownLocation(this).getLocation();
    if (lastKnown != null) setDeviceLocation(deviceLoc, lastKnown);
    else useGPS(deviceLoc);
  }

  private void useGPS(Location deviceLoc) {
    gps = new GPSLocation(this, location -> {
      if (location.getAccuracy() < 100) {
        gps.stopUpdates();
        setDeviceLocation(deviceLoc, location);
      }
    }).startUpdates();
  }

  private void checkPermissions() {
    if (!PermissionManager.hasLocationPermissions(this)) PermissionManager.requestLocationPermissions(this);
  }

  private void setDeviceLocation(Location deviceLoc, android.location.Location location) {
    adaptLocation(deviceLoc, location);
    LocationsDao.addLocation(deviceLoc);
    displayAlerts();
  }

  private void displayAlerts() {
    Intent alertListIntent = new Intent(LoadingActivity.this, AlertListActivity.class);
    startActivity(alertListIntent);
  }

  private void adaptLocation(Location loc, android.location.Location deviceLoc) {
    loc.setLatitude(deviceLoc.getLatitude());
    loc.setLongitude(deviceLoc.getLongitude());
  }
}