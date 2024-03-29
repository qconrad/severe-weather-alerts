package com.severeweatheralerts.Location;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.severeweatheralerts.BroadcastReceivers.LocationReceiver;
import com.severeweatheralerts.Constants;

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
    LocationRequest request = LocationRequest.create();
    request.setInterval(Constants.LOCATION_COMPUTE_INTERVAL);
    request.setFastestInterval(Constants.FASTEST_LOCATION_INTERVAL);
    request.setSmallestDisplacement(Constants.MINIMUM_LOCATION_DISPLACEMENT);
    request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    return request;
  }

  private PendingIntent getPendingIntent() {
    int flags = PendingIntent.FLAG_UPDATE_CURRENT;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      flags = flags | PendingIntent.FLAG_IMMUTABLE;
    }
    return PendingIntent.getBroadcast(context, 0, getIntent(), flags);
  }

  private Intent getIntent() {
    return new Intent(context, LocationReceiver.class).setAction(LocationReceiver.ACTION_PROCESS_UPDATES).setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
  }
}
