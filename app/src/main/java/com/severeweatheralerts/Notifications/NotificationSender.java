package com.severeweatheralerts.Notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import com.severeweatheralerts.Activities.NotificationViewer;
import com.severeweatheralerts.AlertExtrasGenerator;
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
    Intent resultIntent = new Intent(context, NotificationViewer.class);
    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
    stackBuilder.addNextIntentWithParentStack(resultIntent);
    new AlertExtrasGenerator(alert, resultIntent).addExtras();
    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

    NotificationContentGenerator notificationContent = new NotificationContentGenerator(alert);
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel)
            .setSmallIcon(alert.getIcon())
            .setAutoCancel(true)
            .setContentTitle(notificationContent.getTitleText())
            .setContentText(notificationContent.getShortText())
            .setContentIntent(resultPendingIntent)
            .setColor(alert.getColor())
            .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(notificationContent.getLongText()));

    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
    notificationManager.notify(alert.getName().hashCode(), builder.build());
  }
}
