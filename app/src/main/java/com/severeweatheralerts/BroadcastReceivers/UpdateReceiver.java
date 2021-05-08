package com.severeweatheralerts.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;

import androidx.preference.PreferenceManager;

import com.severeweatheralerts.Location.BackgroundLocation;
import com.severeweatheralerts.Location.LastKnownLocation;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.PermissionManager;
import com.severeweatheralerts.UserSync.UserSyncWorkScheduler;

public class UpdateReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    if (!intent.getAction().equals(Intent.ACTION_MY_PACKAGE_REPLACED)) return;
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    if (oldVersion(preferences)) adaptToNewVersion(context, preferences);
    setBackgroundLocation(context, preferences);
  }

  private void setBackgroundLocation(Context context, SharedPreferences preferences) {
    if (!preferences.getBoolean("use_fixed", false)) {
      new BackgroundLocation(context).start();
    }
  }

  private boolean oldVersion(SharedPreferences preferences) {
    return preferences.contains("lastSentLocation");
  }

  private void adaptToNewVersion(Context context, SharedPreferences preferences) {
    LocationsDao dao = LocationsDao.getInstance(context);
    if (PermissionManager.hasCoarseLocation(context)) {
      Location lastLocation = new LastKnownLocation(context).getLocation();
      if (lastLocation != null) setLocation(dao, lastLocation.getLatitude(), lastLocation.getLongitude());
      else getLocationFromPreferences(dao, preferences);
    }
    else getLocationFromPreferences(dao, preferences);

    SharedPreferences.Editor editor = preferences.edit();
    editor.clear();
    if (LocationsDao.getInstance(context).hasLocations()) {
      editor.putBoolean("first_run", false);
      new UserSyncWorkScheduler(context).oneTimeSync();
    }
    editor.apply();
  }

  private void getLocationFromPreferences(LocationsDao dao, SharedPreferences preferences) {
    String[] lastSentLocation = preferences.getString("lastSentLocation", "").split(",");
    if (lastSentLocation.length == 2) {
      setLocation(dao, Double.parseDouble(lastSentLocation[0]), Double.parseDouble(lastSentLocation[1]));
    }
  }

  private void setLocation(LocationsDao dao, double latitude, double longitude) {
    dao.setDefaultLocation("Last Known Location", latitude, longitude);
  }
}
