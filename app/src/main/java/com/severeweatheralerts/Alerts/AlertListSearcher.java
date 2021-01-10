package com.severeweatheralerts.Alerts;

import java.util.ArrayList;

public class AlertListSearcher {
  private final ArrayList<Alert> alerts;

  public AlertListSearcher(ArrayList<Alert> alerts) {
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
