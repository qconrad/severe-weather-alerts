package com.severeweatheralerts.Adapters;

import com.severeweatheralerts.AlertListTools.AlertFinder;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.TextUtils.DateTimeConverter;

import java.util.ArrayList;

public class DiscontinuationChecker {
  private final ArrayList<UnadaptedAlert> unadaptedAlerts;
  private final ArrayList<Alert> alerts;

  public DiscontinuationChecker(ArrayList<UnadaptedAlert> unadaptedAlerts, ArrayList<Alert> alerts) {
    this.unadaptedAlerts = unadaptedAlerts;
    this.alerts = alerts;
  }

  public ArrayList<Alert> getAlerts() {
    for (int i = 0; i < unadaptedAlerts.size(); i++) {
      if (new AlertFinder(alerts).findAlertByID(unadaptedAlerts.get(i).getReplacedBy()) == null) {
        alerts.get(i).setDiscontinuedAt(DateTimeConverter.convertStringToDate(unadaptedAlerts.get(i).getReplacedAt()));
      }
    }
    return alerts;
  }
}
