package com.severeweatheralerts.Preferences;

import static com.severeweatheralerts.Alerts.Alert.*;

public class PreferenceStringGenerator {
  private final String type;;
  private final String alertName;

  public PreferenceStringGenerator(String alertName, Type type) {
    this.type = type.toString();
    this.alertName = alertName;
  }

  public String getString() {
    return type + alertName;
  }
}
