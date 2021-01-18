package com.severeweatheralerts.TextGeneraters;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.TimeFormatters.AbsoluteTimeFormatter;

import java.util.Date;
import java.util.TimeZone;

public class NextUpdate {
  private final Alert alert;

  public NextUpdate(Alert alert) {
    this.alert = alert;
  }

  public boolean hasText() {
    if (isCancel()) return false;
    return alert.getExpectedUpdateTime() != null;
  }

  public String getText(Date time) {
    if (hasText() && !isCancel()) {
      if (alert.isLikelyLastUpdate()) return "Likely to be the last update";
      return "Next update expected by " + new AbsoluteTimeFormatter(time, alert.getExpectedUpdateTime(), TimeZone.getDefault()).getFormattedString();
    }
    return null;
  }

  private boolean isCancel() {
    return alert.getType() == Alert.Type.CANCEL;
  }
}
