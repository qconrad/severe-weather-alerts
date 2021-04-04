package com.severeweatheralerts;

import android.content.Intent;

import com.severeweatheralerts.Alerts.Alert;

public class AlertExtrasGenerator {
  private final Intent resultIntent;
  private final Alert alert;

  public AlertExtrasGenerator(Alert alert, Intent resultIntent) {
    this.resultIntent = resultIntent;
    this.alert = alert;
  }

  public Intent addExtras() {
    return resultIntent.putExtra("name", alert.getName())
            .putExtra("sent", (int)alert.getSentTime().getTime())
            .putExtra("start", (int)alert.getStartTime().getTime())
            .putExtra("ends", (int)alert.getEndTime().getTime());
  }
}