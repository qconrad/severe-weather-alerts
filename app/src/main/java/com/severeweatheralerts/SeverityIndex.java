package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;

public class SeverityIndex {
  private final int urgency;
  private final int certainty;
  private final int severity;

  public SeverityIndex(Alert alert) {
    this.urgency = alert.getUrgency().ordinal();
    this.certainty = alert.getCertainty().ordinal();
    this.severity = alert.getSeverity().ordinal();
  }

  public int get() {
    return (3 * severity) + urgency + certainty;
  }
}
