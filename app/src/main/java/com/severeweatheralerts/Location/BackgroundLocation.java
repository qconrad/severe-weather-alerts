package com.severeweatheralerts.Location;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.severeweatheralerts.BroadcastReceivers.LocationReceiver;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class BackgroundLocation {
  private final Context context;
  private final FusedLocationProviderClient fusedLocationProviderClient;

  public BackgroundLocation(Context context) {
    this.context = context;
    fusedLocationProviderClient = getFusedLocationProviderClient(context);
  }

  public void start() {
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;
    fusedLocationProviderClient.requestLocationUpdates(getRequest(), getPendingIntent());
  }

  public void stop() {
    fusedLocationProviderClient.removeLocationUpdates(getPendingIntent());
  }

  private LocationRequest getRequest() {
    LocationRequest mLocationRequest = LocationRequest.create();
    mLocationRequest.setInterval(20 * 60 * 1000);
    mLocationRequest.setFastestInterval(5 * 60 * 1000);
    mLocationRequest.setSmallestDisplacement(200);
    mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    return mLocationRequest;
  }

  private PendingIntent getPendingIntent() {
    return PendingIntent.getBroadcast(context, 0, getIntent(), PendingIntent.FLAG_UPDATE_CURRENT);
  }

  private Intent getIntent() {
    return new Intent(context, LocationReceiver.class).setAction(LocationReceiver.ACTION_PROCESS_UPDATES);
  }
}
