package com.severeweatheralerts;

import android.os.Bundle;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.AlertFactory;

import java.util.Date;

public class BundleAlertAdapter {
  private final Bundle bundle;

  public BundleAlertAdapter(Bundle bundle) {
    this.bundle = bundle;
  }

  public Alert getAlert() {
    Alert alert = new AlertFactory().getAlert(bundle.getString("name"));
    alert.setName(bundle.getString("name"));
    alert.setDescription(bundle.getString("description"));
    alert.setInstruction(bundle.getString("instruction"));
    alert.setLargeHeadline(bundle.getString("largeHeadline"));
    alert.setSmallHeadline(bundle.getString("smallHeadline"));
    alert.setSentTime(new Date(bundle.getLong("sent")));
    alert.setStartTime(new Date(bundle.getLong("start")));
    alert.setEndTime(new Date(bundle.getLong("ends")));
    String type = bundle.getString("type");;
    if (type != null) alert.setType(Alert.Type.valueOf(type));
    alert.setSenderCode(bundle.getString("senderCode"));
    return alert;
  }
}
