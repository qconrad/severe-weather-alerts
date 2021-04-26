package com.severeweatheralerts.Location.Geofencing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.severeweatheralerts.Location.GPSLocation;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.UserSync.UserSyncWorkScheduler;

import io.paperdb.Paper;

public class GeofenceReceiver extends BroadcastReceiver {
  private GPSLocation gpsLocation;
  @Override
  public void onReceive(Context context, Intent intent) {
    LocationsDao locationsDao = new LocationsDao(context);
    GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
    int geofenceTransition = geofencingEvent.getGeofenceTransition();
    if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
      Location location = geofencingEvent.getTriggeringLocation();
      float radius = Math.max(600 * location.getSpeed(), 5000);
      new GeofenceManager(context).setMovingGeofence(location.getLatitude(), location.getLongitude(), radius, 1200000);
      locationsDao.updateDefaultLocation(location.getLatitude(), location.getLongitude());
      new UserSyncWorkScheduler(context).oneTimeSync();
    } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
      gpsLocation = new GPSLocation(context, location -> {
        if (location.getAccuracy() < 200) {
          gpsLocation.stopUpdates();
          locationsDao.updateDefaultLocation(location.getLatitude(), location.getLongitude());
          new GeofenceManager(context).setStationaryGeofence(location.getLatitude(), location.getLongitude(), 500);
          new UserSyncWorkScheduler(context).oneTimeSync();
        }
      }).startUpdates();
    }
  }
}
