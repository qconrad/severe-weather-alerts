package com.severeweatheralerts.Notifications;

import static com.severeweatheralerts.FileDB.getLocationsDao;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.severeweatheralerts.Activities.NotificationViewer;
import com.severeweatheralerts.Alerts.Alert;

public class NotificationSender {
  private final Context context;
  private final Alert alert;
  private final String channel;
  private final int locationIndex;

  public NotificationSender(Context context, Alert alert, String channel, int locationIndex) {
    this.context = context;
    this.alert = alert;
    this.channel = channel;
    this.locationIndex = locationIndex;
  }

  public void send() {
    NotificationContentGenerator notificationContent = new NotificationContentGenerator(alert);
    int notifyID = new NotificationIdGenerator(alert, locationIndex).generateId().hashCode();
    if (notDefaultLocation())
      addLocationNameToContent(notificationContent);
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel)
            .setSmallIcon(alert.getIcon())
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentTitle(notificationContent.getTitleText())
            .setContentText(notificationContent.getShortText())
            .setContentIntent(getPendingIntent(notifyID))
            .setColor(alert.getColor())
            .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(notificationContent.getLongText()));

    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
    notificationManager.notify(notifyID, builder.build());
  }

  private void addLocationNameToContent(NotificationContentGenerator notificationContent) {
    notificationContent.setLocationName(getLocationName());
  }

  private String getLocationName() {
    return getLocationsDao(context).getLocation(locationIndex).getName();
  }

  private boolean notDefaultLocation() {
    return locationIndex != 0;
  }

  private PendingIntent getPendingIntent(int notifyID) {
    Intent viewerIntent = new Intent(context, NotificationViewer.class);
    viewerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    new AlertExtrasGenerator(alert, viewerIntent, locationIndex).addExtras();
    int flags = PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_UPDATE_CURRENT;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
      flags = flags | PendingIntent.FLAG_IMMUTABLE;
    }
    return PendingIntent.getActivity(context, notifyID, viewerIntent, flags);
  }
}
