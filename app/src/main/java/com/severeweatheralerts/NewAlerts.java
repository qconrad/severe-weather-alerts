package com.severeweatheralerts;

import com.severeweatheralerts.Location.NewAlertCallback;

public class NewAlerts {
  private static boolean hasNewAlerts = false;
  private static NewAlertCallback newAlertCallback;

  // Returns true if new alerts are available that have not been acknowledged()
  public static boolean hasNewAlerts() {
    return hasNewAlerts;
  }

  // Call this to signal that a new alert/message came in and the user should refresh to get the latest data
  public static void alertReceived() {
    if (newAlertCallback != null) newAlertCallback.newAlerts();
    hasNewAlerts = true;
  }

  // Static event listener (can only have one active)
  public static void onNewAlerts(NewAlertCallback newAlertCallback) {
    NewAlerts.newAlertCallback = newAlertCallback;
  }

  // Call to indicate that the user has the latest data
  public static void acknowledged() {
    hasNewAlerts = false;
  }
}
