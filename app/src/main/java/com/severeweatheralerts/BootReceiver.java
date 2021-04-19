package com.severeweatheralerts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Location.Geofencing.GeofenceManager;
import com.severeweatheralerts.Location.LocationsDao;

import io.paperdb.Paper;

public class BootReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    Paper.init(context);
    if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) && !usingFixedLocation(context)) {
      GCSCoordinate coordinate = LocationsDao.getCoordinate(0);
      new GeofenceManager(context).setStationaryGeofence(coordinate.getLat(), coordinate.getLong(), 500);
    }
  }

  private boolean usingFixedLocation(Context context) {
    return context.getSharedPreferences("status", Context.MODE_PRIVATE).getBoolean("usefixed", false);
  }
}
