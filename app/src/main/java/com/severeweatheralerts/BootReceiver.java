package com.severeweatheralerts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.preference.PreferenceManager;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Location.Geofencing.GeofenceManager;
import com.severeweatheralerts.Location.LocationsDao;

public class BootReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) && !usingFixedLocation(context)) {
      GCSCoordinate coordinate = LocationsDao.getInstance(context).getCoordinate(0);
      new GeofenceManager(context).setStationaryGeofence(coordinate.getLat(), coordinate.getLong(), 500);
    }
  }

  private boolean usingFixedLocation(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("usefixed", false);
  }
}
