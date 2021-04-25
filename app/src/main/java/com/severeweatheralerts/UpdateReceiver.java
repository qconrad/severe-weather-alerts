package com.severeweatheralerts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;

import androidx.preference.PreferenceManager;

import com.severeweatheralerts.Location.LastKnownLocation;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.UserSync.UserSyncWorkScheduler;

import io.paperdb.Paper;

public class UpdateReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    if (!intent.getAction().equals(Intent.ACTION_MY_PACKAGE_REPLACED)) return;
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    if (oldVersion(preferences)) adaptToNewVersion(context, preferences);
  }

  private boolean oldVersion(SharedPreferences preferences) {
    return preferences.contains("lastSentLocation");
  }

  private void adaptToNewVersion(Context context, SharedPreferences preferences) {
    Paper.init(context);
    if (PermissionManager.hasCoarseLocation(context)) {
      Location lastLocation = new LastKnownLocation(context).getLocation();
      if (lastLocation != null) setLocation(lastLocation.getLatitude(), lastLocation.getLongitude());
      else getLocationFromPreferences(preferences);
    }
    else getLocationFromPreferences(preferences);

    SharedPreferences.Editor editor = preferences.edit();
    editor.clear();
    if (LocationsDao.hasLocations()) {
      editor.putBoolean("first_run", false);
      new UserSyncWorkScheduler(context).oneTimeSync();
    }
    editor.apply();
  }

  private void getLocationFromPreferences(SharedPreferences preferences) {
    String[] lastSentLocation = preferences.getString("lastSentLocation", "").split(",");
    if (lastSentLocation.length == 2) {
      setLocation(Double.parseDouble(lastSentLocation[0]), Double.parseDouble(lastSentLocation[1]));
    }
  }

  private void setLocation(double latitude, double longitude) {
    LocationsDao.setDefaultLocation("Last Known Location", latitude, longitude);
  }
}
