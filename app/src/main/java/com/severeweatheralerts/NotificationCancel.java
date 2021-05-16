package com.severeweatheralerts;

import android.content.Context;

import androidx.core.app.NotificationManagerCompat;

import com.severeweatheralerts.Alerts.Alert;

public class NotificationCancel {
  private final Context context;
  private final Alert alert;

  public NotificationCancel(Context context, Alert alert) {
    this.context = context;
    this.alert = alert;
  }

  public void cancel() {
    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
    notificationManager.cancel(alert.getName().hashCode());
  }
}
