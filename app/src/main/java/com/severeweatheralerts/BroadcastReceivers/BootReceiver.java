package com.severeweatheralerts.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.preference.PreferenceManager;

import com.severeweatheralerts.Location.BackgroundLocation;

public class BootReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) && !usingFixedLocation(context)) {
      new BackgroundLocation(context).start();
    }
  }

  private boolean usingFixedLocation(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("usefixed", false);
  }
}
