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
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel)
            .setContentTitle(alert.getName())
            .setSmallIcon(alert.getIcon())
            .setColor(alert.getColor())
            .setContentText("Content text")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
    notificationManager.notify(0, builder.build());
  }
}
