package com.severeweatheralerts;

import com.severeweatheralerts.Location.NewAlertCallback;

public class NewAlerts {
  private static boolean hasNewAlerts = false;
  private static NewAlertCallback newAlertCallback;

  public static boolean hasNewAlerts() {
    return hasNewAlerts;
  }

  public static void alertReceived() {
    if (newAlertCallback != null) newAlertCallback.newAlerts();
    hasNewAlerts = true;
  }

  public static void onNewAlerts(NewAlertCallback newAlertCallback) {
    NewAlerts.newAlertCallback = newAlertCallback;
  }
}
