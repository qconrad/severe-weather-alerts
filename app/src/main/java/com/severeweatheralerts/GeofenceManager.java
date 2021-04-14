package com.severeweatheralerts;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

public class GeofenceManager {
  private final Context context;
  private final GeofencingClient geofencingClient;

  public GeofenceManager(Context context) {
    this.context = context;
    geofencingClient = LocationServices.getGeofencingClient(context);
  }

  public void setGeofence(double lat, double lon, float radius) {
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;
    geofencingClient.removeGeofences(getGeofencePendingIntent());
    geofencingClient.addGeofences(getGeofencingRequest(lat, lon, radius), getGeofencePendingIntent());
  }

  private GeofencingRequest getGeofencingRequest(double lat, double lon, float radius) {
    ArrayList<Geofence> geofenceList = new ArrayList<>();
    geofenceList.add(new Geofence.Builder()
            .setRequestId("locationChangeUpdate")
            .setCircularRegion(lat, lon, radius)
            .setExpirationDuration(-1)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_EXIT)
            .build());

    GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
    builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
    builder.addGeofences(geofenceList);
    return builder.build();
  }

  private PendingIntent getGeofencePendingIntent() {
    Intent intent = new Intent(context, GeofenceReceiver.class);
    return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
  }

}
