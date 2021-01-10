package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;

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

  public void setAlerts(ArrayList<Alert> alertList) {
    this.alerts = alertList;
  }
}
