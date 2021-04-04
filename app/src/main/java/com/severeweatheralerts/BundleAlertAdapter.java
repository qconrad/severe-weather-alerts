package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;

import java.util.Map;

public class BundleAlertAdapter {
  private final Map<String, String> map;

  public BundleAlertAdapter(Map<String, String> map) {
    this.map = map;
  }

  public Alert getAlert() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setName(map.get("name"));
    return defaultAlert;
  }
}
