package com.severeweatheralerts.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.severeweatheralerts.Location.GPSLocation;
import com.severeweatheralerts.Location.LastKnownLocation;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.Networking.LocationPopulaters.FromLocationPointPopulater;
import com.severeweatheralerts.PermissionManager;
import com.severeweatheralerts.R;

import java.io.IOException;

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
      if (accurateEnough(location)) useLocation(deviceLoc, location);
    }).startUpdates();
  }

  private void useLocation(Location deviceLoc, android.location.Location location) {
    gps.stopUpdates();
    setDeviceLocation(deviceLoc, location);
  }

  private boolean accurateEnough(android.location.Location location) {
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
    new AsyncRefresh().execute();
  }

  private void displayAlerts() {
    Intent alertListIntent = new Intent(LoadingActivity.this, AlertListActivity.class);
    startActivity(alertListIntent);
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }

  private void adaptLocation(Location loc, android.location.Location deviceLoc) {
    loc.setLatitude(deviceLoc.getLatitude());
    loc.setLongitude(deviceLoc.getLongitude());
  }

  private class AsyncRefresh extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... params) {
      try { populateAlerts(); }
      catch (IOException e) { e.printStackTrace(); }
      return null;
    }

    private void populateAlerts() throws IOException {
      new FromLocationPointPopulater(LocationsDao.getLocation(0)).populate();
    }

    @Override
    protected void onPostExecute(Void result) {
      displayAlerts();
    }
  }
}