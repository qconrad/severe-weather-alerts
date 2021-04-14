package com.severeweatheralerts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.location.GeofencingEvent;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.UserSync.UserSyncWorkScheduler;

import io.paperdb.Paper;

public class GeofenceReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    Paper.init(context);
    GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
    Location location = geofencingEvent.getTriggeringLocation();
    float radius = (200 * location.getSpeed()) + 400;
    new GeofenceManager(context).setGeofence(location.getLatitude(), location.getLongitude(), radius);
    LocationsDao.updateDefaultLocation(location.getLatitude(), location.getLongitude());
    new UserSyncWorkScheduler(context).oneTimeSync();
  }
}
