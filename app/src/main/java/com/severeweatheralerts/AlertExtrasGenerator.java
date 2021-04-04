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
            .putExtra("description", alert.getDescription())
            .putExtra("instruction", alert.getInstruction())
            .putExtra("largeHeadline", alert.getLargeHeadline())
            .putExtra("smallHeadline", alert.getSmallHeadline())
            .putExtra("sent", alert.getSentTime().getTime())
            .putExtra("start", alert.getStartTime().getTime())
            .putExtra("ends", alert.getEndTime().getTime())
            .putExtra("type", alert.getType().toString())
            .putExtra("senderCode", alert.getSenderCode());
  }
}
