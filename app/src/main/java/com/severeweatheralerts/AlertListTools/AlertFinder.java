package com.severeweatheralerts.AlertListTools;

import com.severeweatheralerts.Alerts.Alert;

import java.util.ArrayList;

public class AlertFinder {
  private final ArrayList<Alert> alerts;

  public AlertFinder(ArrayList<Alert> alerts) {
    this.alerts = alerts;
  }

  public Alert findAlertByID(String id) {
    for (int i = 0; i < alerts.size(); i++) {
      Alert curAlert = alerts.get(i);
      if (matchingIDs(id, curAlert)) return curAlert;
    }
    return null;
  }

  private boolean matchingIDs(String id, Alert alert) {
    return alert.getNwsId().equals(id);
  }
}
