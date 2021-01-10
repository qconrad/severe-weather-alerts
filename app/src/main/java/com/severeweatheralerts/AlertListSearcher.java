package com.severeweatheralerts;

import java.util.ArrayList;

public class AlertListSearcher {
  private final ArrayList<Alert> alerts;

  public AlertListSearcher(ArrayList<Alert> alerts) {
    this.alerts = alerts;
  }

  public Alert findAlertByID(String testID) {
    for (int i = 0; i < alerts.size(); i++) {
      Alert curAlert = alerts.get(i);
      if (matchingIDs(testID, curAlert))
        return curAlert;
    }
    return null;
  }

  private boolean matchingIDs(String testID, Alert alert) {
    return alert.getNwsId().equals(testID);
  }
}
