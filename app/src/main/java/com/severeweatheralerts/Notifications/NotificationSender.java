package com.severeweatheralerts.Notifications;

import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.severeweatheralerts.Alerts.Alert;

public class NotificationSender {
  private final Context context;
  private final Alert alert;
  private final String channel;

  public NotificationSender(Context context, Alert alert, String channel) {
    this.context = context;
    this.alert = alert;
    this.channel = channel;
  }

  public void send() {
    NotificationContentGenerator notificationContent = new NotificationContentGenerator(alert);
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel)
            .setSmallIcon(alert.getIcon())
            .setContentTitle(notificationContent.getTitleText())
            .setContentText(notificationContent.getShortText())
            .setColor(alert.getColor())
            .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(notificationContent.getLongText()));

    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
    notificationManager.notify(alert.getName().hashCode(), builder.build());
  }
}
