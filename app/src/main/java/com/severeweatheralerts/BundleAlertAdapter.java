package com.severeweatheralerts;

import android.os.Bundle;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;

import java.util.Date;

public class BundleAlertAdapter {
  private final Bundle bundle;

  public BundleAlertAdapter(Bundle bundle) {
    this.bundle = bundle;
  }

  public Alert getAlert() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setName(bundle.getString("name"));
    defaultAlert.setDescription(bundle.getString("description"));
    defaultAlert.setSentTime(new Date(bundle.getLong("sent")));
    defaultAlert.setStartTime(new Date(bundle.getLong("start")));
    defaultAlert.setEndTime(new Date(bundle.getLong("ends")));
    String type = bundle.getString("type");;
    if (type != null) defaultAlert.setType(Alert.Type.valueOf(type));
    defaultAlert.setSenderCode(bundle.getString("senderCode"));
    return defaultAlert;
  }
}
