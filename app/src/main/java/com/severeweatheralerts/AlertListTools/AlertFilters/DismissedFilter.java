package com.severeweatheralerts.AlertListTools.AlertFilters;

import com.severeweatheralerts.Alerts.Alert;

import java.util.ArrayList;
import java.util.Set;

public class DismissedFilter extends AlertFilter {
  private final Set<String> dismissedIDs;

  public DismissedFilter(ArrayList<Alert> alerts, Set<String> dismissedIDs) {
    super(alerts);
    this.dismissedIDs = dismissedIDs;
  }

  @Override
  protected boolean shouldFilterAlert(Alert alert) {
    return dismissedIDs.contains(alert.getNwsId());
  }
}
