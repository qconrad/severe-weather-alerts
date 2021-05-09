package com.severeweatheralerts.Location;

import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.R;
import com.severeweatheralerts.UserSync.UserSyncWorkScheduler;

public class ConditionalDefaultLocationSync {
  private final Context context;
  private final double lat;
  private final double lon;
  private final LocationsDao locationsDao;

  public ConditionalDefaultLocationSync(Context context, double lat, double lon) {
    this.context = context;
    this.lat = lat;
    this.lon = lon;
    locationsDao = LocationsDao.getInstance(context);
  }

  public synchronized void sync() {
    updateLocationInDao();
    syncIfLocationChanged();
  }

  private void syncIfLocationChanged() {
    if (!isDifferent()) return;
    sendDebugNotification();
    scheduleSync();
    setLastSync();
  }

  private void setLastSync() {
    locationsDao.setLastDefaultSync(new GCSCoordinate(lat, lon));
  }

  private void scheduleSync() {
    new UserSyncWorkScheduler(context).oneTimeSync();
  }

  private void sendDebugNotification() {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "high")
            .setSmallIcon(R.drawable.hazard)
            .setContentTitle("Receiver")
            .setContentText(lat + ", " + lon)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true);
    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
    notificationManager.notify(0, builder.build());
  }

  private boolean isDifferent() {
    return new CoordinateDifference(locationsDao.getLastDefaultSync(), new GCSCoordinate(lat, lon)).isDifferent(0.001);
  }

  private void updateLocationInDao() {
    locationsDao.updateDefaultLocation(lat, lon);
  }
}
