package com.severeweatheralerts.AlertListTools;

import com.severeweatheralerts.Alerts.Alert;

import java.util.ArrayList;

public class ReplacementFilter extends AlertFilter {
  public ReplacementFilter(ArrayList<Alert> alerts) {
    super(alerts);
  }

  @Override
  protected boolean shouldFilterAlert(Alert alert) {
    return alert.isReplaced();
  }
}
