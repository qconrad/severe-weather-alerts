package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.AlertFactory;

import java.util.Map;

public class MessageAdapter {
  private final Map<String, String> alertMessage;

  public MessageAdapter(Map<String, String> alertMessage) {
    this.alertMessage = alertMessage;
  }

  public Alert getAlert() {
    Alert alert = new AlertFactory().getAlert(alertMessage.get("name"));
    alert.setName(alertMessage.get("name"));
    alert.setDescription(alertMessage.get("description"));
    alert.setInstruction(alertMessage.get("instruction"));
    return alert;
  }
}
