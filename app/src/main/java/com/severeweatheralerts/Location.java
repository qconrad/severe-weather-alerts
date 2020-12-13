package com.severeweatheralerts;

import java.util.ArrayList;

public class Location {
  ArrayList<Alert> alerts;
  public Location() {
    alerts = new ArrayList<>();
  }

  public ArrayList<Alert> getAlerts() {
    return alerts;
  }

  public void addAlert(Alert alert) {
    alerts.add(alert);
  }
}
