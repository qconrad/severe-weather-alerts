package com.severeweatheralerts.BroadcastReceivers;

import static com.severeweatheralerts.FileDB.getLocationsDao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;

import androidx.preference.PreferenceManager;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Location.BackgroundLocation;
import com.severeweatheralerts.Location.LastKnownLocation;
import com.severeweatheralerts.Permissions.PermissionManager;
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
    if (!preferences.getBoolean("use_fixed", false)) new BackgroundLocation(context).start();
  }

  private boolean oldVersion(SharedPreferences preferences) {
    return preferences.contains("lastSentLocation");
  }

  private void adaptToNewVersion(Context context, SharedPreferences preferences) {
    if (PermissionManager.hasCoarseLocation(context)) {
      Location lastLocation = new LastKnownLocation(context).getLocation();
      if (lastLocation != null) setLocation(context, lastLocation.getLatitude(), lastLocation.getLongitude());
      else getLocationFromPreferences(context, preferences);
    }
    else getLocationFromPreferences(context, preferences);

    SharedPreferences.Editor editor = preferences.edit();
    editor.clear();
    if (!getLocationsDao(context).getDefaultLocation().coordinateSet()) {
      new UserSyncWorkScheduler(context).oneTimeSync();
    }
    editor.apply();
  }

  private void getLocationFromPreferences(Context context, SharedPreferences preferences) {
    String[] lastSentLocation = preferences.getString("lastSentLocation", "").split(",");
    if (lastSentLocation.length == 2) {
      setLocation(context, Double.parseDouble(lastSentLocation[0]), Double.parseDouble(lastSentLocation[1]));
    }
  }

  private void setLocation(Context context, double latitude, double longitude) {
    getLocationsDao(context).setDefaultLocation(getLocationsDao(context).getDefaultLocation().setName("Last Known Location").setCoordinate(new GCSCoordinate(latitude, longitude)));
  }
}
