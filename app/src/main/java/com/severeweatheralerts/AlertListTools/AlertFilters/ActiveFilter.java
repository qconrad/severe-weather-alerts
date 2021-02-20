package com.severeweatheralerts.AlertListTools.AlertFilters;

import com.severeweatheralerts.Alerts.Alert;

import java.util.ArrayList;
import java.util.Date;

public class ActiveFilter extends InactiveFilter {
  public ActiveFilter(ArrayList<Alert> alerts, Date date) {
    super(alerts, date);
  }

  @Override
  protected boolean shouldFilterAlert(Alert alert) {
    return !super.shouldFilterAlert(alert);
  }
}
