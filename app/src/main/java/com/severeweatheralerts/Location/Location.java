package com.severeweatheralerts.Location;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Preferences.ChannelPreferences;

import java.util.ArrayList;

public class Location {
  private String name;
  private GCSCoordinate coordinate;
  private transient ArrayList<Alert> alerts;
  private ChannelPreferences channelPreferences;
  private boolean notificationsEnabled = true;

  public Location() {
    alerts = new ArrayList<>();
  }

  public Location(String name) {
    this();
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public Location setName(String name) {
    this.name = name;
    return this;
  }
  public ArrayList<Alert> getAlerts() {
    return alerts;
  }
  public void addAlert(Alert alert) {
    alerts.add(alert);
  }

  public Location setAlerts(ArrayList<Alert> alertList) {
    this.alerts = alertList;
    return this;
  }

  public ChannelPreferences getChannelPreferences() {
    return channelPreferences;
  }

  public Location setChannelPreferences(ChannelPreferences channelPreferences) {
    this.channelPreferences = channelPreferences;
    return this;
  }

  public Location setCoordinate(GCSCoordinate coordinate) {
    this.coordinate = coordinate;
    return this;
  }

  public GCSCoordinate getCoordinate() {
    return coordinate;
  }

  public boolean isNotifying() {
    return notificationsEnabled;
  }

  public void setNotify(boolean notificationsEnabled) {
    this.notificationsEnabled = notificationsEnabled;
  }

  public boolean coordinateSet() {
    return coordinate != null;
  }
}
