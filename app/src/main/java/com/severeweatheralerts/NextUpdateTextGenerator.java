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
    if (isCancel()) return false;
    return alert.getExpectedUpdateTime() != null;
  }

  public String getText(Date time) {
    if (hasText() && !isCancel()) {
      if (alert.isLikelyLastUpdate()) return "Likely to be the last update";
      return "Next update expected by " + new AbsoluteTimeFormatter(time, alert.getExpectedUpdateTime()).getFormattedString();
    }
    return null;
  }

  private boolean isCancel() {
    return alert.getType() == Alert.Type.CANCEL;
  }
}
