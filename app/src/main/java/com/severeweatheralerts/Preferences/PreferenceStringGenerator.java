package com.severeweatheralerts.Preferences;

import com.severeweatheralerts.Alerts.Alert;

public class PreferenceStringGenerator {
  private final int locationIndex;
  private final String type;;
  private final String alertName;

  public PreferenceStringGenerator(int locationIndex, Alert.Type type, String alertName) {
    this.locationIndex = locationIndex;
    this.type = type.toString();
    this.alertName = alertName;
  }

  public String getString() {
    return locationIndex + type + alertName;
  }
}
