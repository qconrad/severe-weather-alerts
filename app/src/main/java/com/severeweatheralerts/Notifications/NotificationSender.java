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
    NotificationContentGenerator notificationContentGenerator = new NotificationContentGenerator(alert);
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel)
            .setSmallIcon(alert.getIcon())
            .setContentTitle(alert.getName())
            .setContentText(notificationContentGenerator.getShortText())
            .setColor(alert.getColor())
            .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(notificationContentGenerator.getLongText()));

    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
    notificationManager.notify(0, builder.build());
  }
}
