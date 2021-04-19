package com.severeweatheralerts.Location.Geofencing;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.severeweatheralerts.PermissionManager;

import java.util.ArrayList;

public class GeofenceManager {
  private final Context context;
  private final GeofencingClient geofencingClient;

  public GeofenceManager(Context context) {
    this.context = context;
    geofencingClient = LocationServices.getGeofencingClient(context);
  }

  @SuppressLint("MissingPermission")
  public void setMovingGeofence(double lat, double lon, float radius, int dwellTimeMS) {
    if (!PermissionManager.hasLocationPermissions(context)) return;
    geofencingClient.removeGeofences(getGeofencePendingIntent());
    geofencingClient.addGeofences(getMovingRequest(lat, lon, radius, dwellTimeMS), getGeofencePendingIntent());
  }

  @SuppressLint("MissingPermission")
  public void setStationaryGeofence(double lat, double lon, float radius) {
    if (!PermissionManager.hasLocationPermissions(context)) return;
    geofencingClient.removeGeofences(getGeofencePendingIntent());
    geofencingClient.addGeofences(getStationaryRequest(lat, lon, radius), getGeofencePendingIntent());
  }
  public void removeGeofences() {
    geofencingClient.removeGeofences(getGeofencePendingIntent());
  }

  private GeofencingRequest getMovingRequest(double lat, double lon, float radius, int dwellTimeMS) {
    ArrayList<Geofence> geofenceList = new ArrayList<>();
    geofenceList.add(new Geofence.Builder()
            .setRequestId("locationChangeUpdate")
            .setCircularRegion(lat, lon, radius)
            .setExpirationDuration(-1)
            .setLoiteringDelay(dwellTimeMS)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_EXIT | Geofence.GEOFENCE_TRANSITION_DWELL)
            .build());
    return buildRequest(geofenceList);
  }

  private GeofencingRequest getStationaryRequest(double lat, double lon, float radius) {
    ArrayList<Geofence> geofenceList = new ArrayList<>();
    geofenceList.add(new Geofence.Builder()
            .setRequestId("locationChangeUpdate")
            .setCircularRegion(lat, lon, radius)
            .setExpirationDuration(-1)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_EXIT)
            .build());
    return buildRequest(geofenceList);
  }

  private GeofencingRequest buildRequest(ArrayList<Geofence> geofenceList) {
    GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
    builder.setInitialTrigger(Geofence.GEOFENCE_TRANSITION_DWELL);
    builder.addGeofences(geofenceList);
    return builder.build();
  }

  private PendingIntent getGeofencePendingIntent() {
    Intent intent = new Intent(context, GeofenceReceiver.class);
    return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
  }

}
