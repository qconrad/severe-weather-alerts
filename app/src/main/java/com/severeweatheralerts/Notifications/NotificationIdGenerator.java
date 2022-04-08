package com.severeweatheralerts.Notifications;

import com.severeweatheralerts.Alerts.Alert;

public class NotificationIdGenerator {
  private final Alert alert;
  private final int locationIndex;

  public NotificationIdGenerator(Alert alert, int locationIndex) {
    this.alert = alert;
    this.locationIndex = locationIndex;
  }

  public String generateId() {
    return alert.getName().replace(" ", "") + locationIndex;
  }
}
