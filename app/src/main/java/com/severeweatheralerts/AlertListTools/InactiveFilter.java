package com.severeweatheralerts.AlertListTools;

import com.severeweatheralerts.Alerts.Alert;

import java.util.ArrayList;
import java.util.Date;

public class InactiveFilter extends AlertFilter{
  private final Date date;

  public InactiveFilter(ArrayList<Alert> alerts, Date activeAt) {
    super(alerts);
    date = activeAt;
  }

  @Override
  protected boolean shouldFilterAlert(Alert alert) {
    return alert.activeAt(date);
  }
}
