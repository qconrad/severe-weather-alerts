package com.severeweatheralerts;

import android.os.Bundle;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;

import java.util.Date;
import java.util.Map;

public class BundleAlertAdapter {
  private final Bundle bundle;

  public BundleAlertAdapter(Bundle bundle) {
    this.bundle = bundle;
  }

  public Alert getAlert() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setName(bundle.getString("name"));
    defaultAlert.setSentTime(new Date(bundle.getInt("sent")));
    defaultAlert.setStartTime(new Date(bundle.getInt("start")));
    defaultAlert.setEndTime(new Date(bundle.getInt("ends")));
    return defaultAlert;
  }
}