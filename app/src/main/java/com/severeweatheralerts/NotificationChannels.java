package com.severeweatheralerts;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

public class NotificationChannels {
  public static void createNotificationChannels(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + R.raw.alert_tone);
      AudioAttributes att = new AudioAttributes.Builder()
              .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT)
              .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN).build();
      NotificationChannel extremeChannel = new NotificationChannel("ext", "Extreme Priority", NotificationManager.IMPORTANCE_HIGH);
      extremeChannel.enableVibration(true);
      extremeChannel.setSound(sound, att);
      NotificationChannel highChannel = new NotificationChannel("high", "High Priority", NotificationManager.IMPORTANCE_HIGH);
      highChannel.enableVibration(true);
      NotificationChannel mediumChannel = new NotificationChannel("med", "Medium Priority", NotificationManager.IMPORTANCE_LOW);
      NotificationChannel lowChannel = new NotificationChannel("low", "Low Priority", NotificationManager.IMPORTANCE_MIN);
      NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
      notificationManager.createNotificationChannel(extremeChannel);
      notificationManager.createNotificationChannel(highChannel);
      notificationManager.createNotificationChannel(mediumChannel);
      notificationManager.createNotificationChannel(lowChannel);
    }
  }
}
