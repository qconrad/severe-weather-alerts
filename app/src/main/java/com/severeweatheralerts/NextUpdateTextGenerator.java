package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.TimeFormatters.AbsoluteTimeFormatter;

import java.util.Date;

public class NextUpdateTextGenerator {
  private final Alert alert;

  public NextUpdateTextGenerator(Alert alert) {
    this.alert = alert;
  }

  public boolean hasText() {
    return alert.getExpectedUpdateTime() != null;
  }

  public String getText(Date time) {
    if (alert.isLikelyLastUpdate() && hasText())
      return "Likely to be the last update";
    if (hasText())
      return "Next update expected by " + new AbsoluteTimeFormatter(time, alert.getExpectedUpdateTime()).getFormattedString();
    return null;
  }
}
