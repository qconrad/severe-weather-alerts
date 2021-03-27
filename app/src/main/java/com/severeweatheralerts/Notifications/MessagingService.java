package com.severeweatheralerts.Notifications;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.severeweatheralerts.R;

public class MessagingService extends FirebaseMessagingService {
  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    // Check if message contains a data payload.
    if (remoteMessage.getData().size() > 0) {
      NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "ext")
              .setSmallIcon(R.drawable.hazard)
              .setContentTitle("Content Title")
              .setContentText("Content text")
              .setPriority(NotificationCompat.PRIORITY_DEFAULT);

      // Issue the notification.
      NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
      notificationManager.notify(0, builder.build());
    }
  }
}
