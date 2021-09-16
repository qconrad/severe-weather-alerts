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
    channelPreferences = new ChannelPreferences();
  }

  public Location(String name) {
    this();
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public ChannelPreferences getChannelPreferences() {
    return channelPreferences;
  }

  public void setChannelPreferences(ChannelPreferences channelPreferences) {
    this.channelPreferences = channelPreferences;
  }

  public void setCoordinate(GCSCoordinate coordinate) {
    this.coordinate = coordinate;
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
}
