package com.severeweatheralerts.Location;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Preferences.ChannelPreferences;

import java.util.ArrayList;

public class Location {
  private String name;
  private double latitude;
  private double longitude;
  ArrayList<Alert> alerts;
  ChannelPreferences channelPreferences;

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
}
