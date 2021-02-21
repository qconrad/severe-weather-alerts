package com.severeweatheralerts.Adapters;

import com.severeweatheralerts.Alerts.Alert;

public class CertaintyAdapter {
  private final String certainty;
  public CertaintyAdapter(String certainty) {
    this.certainty = certainty;
  }

  public Alert.Certainty adaptSeverity() {
    if (certaintyIsNull()) return null;
    if (certaintyIs("Unknown")) return Alert.Certainty.UNKNOWN;
    else if (certaintyIs("Unlikely")) return Alert.Certainty.UNLIKELY;
    else if (certaintyIs("Possible")) return Alert.Certainty.POSSIBLE;
    else if (certaintyIs("Likely")) return Alert.Certainty.LIKELY;
    else if (certaintyIs("Observed")) return Alert.Certainty.OBSERVED;
    else return Alert.Certainty.UNKNOWN;
  }

  private boolean certaintyIs(String certainty) {
    return this.certainty.equals(certainty);
  }

  private boolean certaintyIsNull() {
    return certainty == null;
  }
}
