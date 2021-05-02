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
    for (int i = 0; i < unadaptedAlerts.size(); i++)
      checkIfDiscontinued(unadaptedAlerts.get(i), alerts.get(i));
    return alerts;
  }

  private void checkIfDiscontinued(UnadaptedAlert unadaptedAlert, Alert alert) {
    if (isDiscontinued(unadaptedAlert)) setDiscontinued(unadaptedAlert, alert);
  }

  private void setDiscontinued(UnadaptedAlert unadaptedAlert, Alert alert) {
    alert.setDiscontinuedAt(DateTimeConverter.convertStringToDate(unadaptedAlert.getReplacedAt()));
  }

  private boolean isDiscontinued(UnadaptedAlert alert) {
    return new AlertFinder(alerts).findAlertByID(alert.getReplacedBy()) == null;
  }
}
