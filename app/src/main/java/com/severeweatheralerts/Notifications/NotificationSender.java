package com.severeweatheralerts.Notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import com.severeweatheralerts.Activities.NotificationViewer;
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
            .setAutoCancel(true)
            .setContentTitle(notificationContent.getTitleText())
            .setContentText(notificationContent.getShortText())
            .setContentIntent(getPendingIntent())
            .setColor(alert.getColor())
            .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(notificationContent.getLongText()));

    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
    notificationManager.notify(alert.getName().hashCode(), builder.build());
  }

  private PendingIntent getPendingIntent() {
    Intent viewerIntent = new Intent(context, NotificationViewer.class);
    viewerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    new AlertExtrasGenerator(alert, viewerIntent).addExtras();
    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
    stackBuilder.addNextIntentWithParentStack(viewerIntent);
    return stackBuilder.getPendingIntent(alert.getName().hashCode(), PendingIntent.FLAG_UPDATE_CURRENT);
  }
}
