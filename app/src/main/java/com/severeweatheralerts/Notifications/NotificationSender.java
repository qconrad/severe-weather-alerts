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
    if (notDefaultLocation())
      addLocationNameToContent(notificationContent);
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel)
            .setSmallIcon(alert.getIcon())
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentTitle(notificationContent.getTitleText())
            .setContentText(notificationContent.getShortText())
            .setContentIntent(getPendingIntent())
            .setColor(alert.getColor())
            .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(notificationContent.getLongText()));

    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
    notificationManager.notify(alert.getName().hashCode(), builder.build());
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

  private PendingIntent getPendingIntent() {
    Intent viewerIntent = new Intent(context, NotificationViewer.class);
    viewerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    new AlertExtrasGenerator(alert, viewerIntent, locationIndex).addExtras();
    int id = new NotificationIdGenerator(alert, locationIndex).generateId().hashCode();
    return PendingIntent.getActivity(context, id, viewerIntent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_UPDATE_CURRENT);
  }
}
