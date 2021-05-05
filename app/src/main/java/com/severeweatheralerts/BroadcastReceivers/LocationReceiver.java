package com.severeweatheralerts.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.location.LocationResult;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.R;
import com.severeweatheralerts.UserSync.UserSyncWorkScheduler;

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
    LocationsDao.getInstance(context).updateDefaultLocation(location.getLatitude(), location.getLongitude());
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "high")
            .setSmallIcon(R.drawable.hazard)
            .setContentTitle("Receiver")
            .setContentText(location.getLatitude() + ", " + location.getLongitude())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true);
    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
    notificationManager.notify(0, builder.build());
    new UserSyncWorkScheduler(context).oneTimeSync();
  }
}
