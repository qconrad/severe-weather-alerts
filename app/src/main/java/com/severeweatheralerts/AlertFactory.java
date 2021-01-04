package com.severeweatheralerts;

public class AlertFactory {
  public Alert getAlert(String name) {
    if (name == null) return new DefaultAlert();
    switch (name) {
      case "Winter Storm Warning": return new WinterStormWarning();
      case "Winter Weather Advisory": return new WinterWeatherAdvisory();
      default: return new DefaultAlert();
    }
  }
}
