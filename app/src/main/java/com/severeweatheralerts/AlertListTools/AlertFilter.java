package com.severeweatheralerts.AlertListTools;

import com.severeweatheralerts.Alerts.Alert;

import java.util.ArrayList;

public abstract class AlertFilter {
  private final ArrayList<Alert> alerts;

  protected abstract boolean shouldFilterAlert(Alert alert);
  public AlertFilter(ArrayList<Alert> alerts) {
    this.alerts = alerts;
  }

  public ArrayList<Alert> filter() {
    ArrayList<Alert> output = getCopy(alerts);
    for (int i = 0; i < output.size(); i++)
      if (shouldFilterAlert(output.get(i))) output.remove(i--);
    return output;
  }

  private ArrayList<Alert> getCopy(ArrayList<Alert> alerts) {
    return (ArrayList<Alert>) alerts.clone();
  }
}
