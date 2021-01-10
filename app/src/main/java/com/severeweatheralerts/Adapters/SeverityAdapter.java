package com.severeweatheralerts.Adapters;

import com.severeweatheralerts.Alerts.Alert;

public class SeverityAdapter {
  private String severity;

  public SeverityAdapter(String severity) {
    this.severity = severity;
  }

  public Alert.Severity adaptSeverity() {
    if (severityIsNull()) return null;
    if (severityIs("Minor")) return Alert.Severity.MINOR;
    else if (severityIs("Moderate")) return Alert.Severity.MODERATE;
    else if (severityIs("Severe")) return Alert.Severity.SEVERE;
    else if (severityIs("Extreme")) return Alert.Severity.EXTREME;
    else return Alert.Severity.UNKNOWN;
  }

  private boolean severityIs(String severityString) {
    return severity.equals(severityString);
  }

  private boolean severityIsNull() {
    return severity == null;
  }
}
