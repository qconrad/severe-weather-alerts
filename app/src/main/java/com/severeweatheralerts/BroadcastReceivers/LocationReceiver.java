package com.severeweatheralerts.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.location.LocationResult;
import com.severeweatheralerts.Location.ConditionalDefaultLocationSync;

public class LocationReceiver extends BroadcastReceiver {
  public static final String ACTION_PROCESS_UPDATES =
          "com.google.android.gms.location.sample.locationupdatespendingintent.action" +
                  ".PROCESS_UPDATES";

  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent == null) return;
    final String action = intent.getAction();
    if (!ACTION_PROCESS_UPDATES.equals(action)) return;
    LocationResult locationResult = LocationResult.extractResult(intent);
    if (locationResult == null) return;
    Location location = locationResult.getLocations().get(0);
    new ConditionalDefaultLocationSync(context, location.getLatitude(), location.getLongitude()).sync();
  }
}
