package com.severeweatheralerts.Adapters;

import com.severeweatheralerts.Alerts.Alert;

public class UrgencyAdapter {
  private final String urgency;

  public UrgencyAdapter(String urgency) {
    this.urgency = urgency;
  }

  public Alert.Urgency adaptSeverity() {
    if (urgencyIs("Unknown")) return Alert.Urgency.UNKNOWN;
    else if (urgencyIs("Past")) return Alert.Urgency.PAST;
    else if (urgencyIs("Future")) return Alert.Urgency.FUTURE;
    else if (urgencyIs("Expected")) return Alert.Urgency.EXPECTED;
    else if (urgencyIs("Immediate")) return Alert.Urgency.IMMEDIATE;
    return Alert.Urgency.UNKNOWN;
  }

  private boolean urgencyIs(String text) {
    return text.equals(urgency);
  }
}
