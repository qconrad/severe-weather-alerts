package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;

import java.util.ArrayList;

public class Location {
  ArrayList<Alert> alerts;
  private double latitude;
  private double longitude;

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

  public void setLatitude(double longitude) {
    this.latitude = longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }
}
