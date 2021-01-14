package com.severeweatheralerts.Location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.HandlerThread;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

import java.util.List;

@SuppressLint("MissingPermission")
public class GPSLocation extends LocationGetter {
  private final LocationUpdateCallback locationUpdateCallback;
  private final FusedLocationProviderClient fusedLocationClient;
  private LocationCallback locationCallback;
  HandlerThread handlerThread;
  private LocationRequest locationRequest;

  public GPSLocation(Context context, LocationUpdateCallback locationUpdateCallback) {
    super(context);
    fusedLocationClient = new FusedLocationProviderClient(context);
    this.locationUpdateCallback = locationUpdateCallback;
  }

  public void startUpdates() {
    createHandlerThread();
    startHandlerThread();
    createLocationCallback();
    createLocationRequest();
    requestLocationUpdates();
  }

  private void requestLocationUpdates() {
    if (hasPermissions())
      fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, handlerThread.getLooper());
  }

  private void createLocationCallback() {
    locationCallback = new LocationCallback() {
      @Override
      public void onLocationResult(LocationResult locationResult) {
        if (locationResult == null) return;
        locationUpdateCallback.onUpdate(getBestLocation(locationResult.getLocations()));
      }
    };
  }

  private Location getBestLocation(List<Location> locations) {
    return locations.get(0);
  }

  private void startHandlerThread() {
    handlerThread.start();
  }

  private void createHandlerThread() {
    handlerThread = new HandlerThread("RequestLocation");
  }

  private void createLocationRequest() {
    locationRequest = LocationRequest.create();
    locationRequest.setInterval(500);
    locationRequest.setFastestInterval(250);
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
  }

  public void stopUpdates() {
    fusedLocationClient.removeLocationUpdates(locationCallback);
    handlerThread.quit();
  }
}
