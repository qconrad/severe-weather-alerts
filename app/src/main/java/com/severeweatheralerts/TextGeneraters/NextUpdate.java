package com.severeweatheralerts.TextGeneraters;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.NWS.DustStormWarning;
import com.severeweatheralerts.Alerts.NWS.FlashFloodWarning;
import com.severeweatheralerts.Alerts.NWS.FloodAdvisory;
import com.severeweatheralerts.Alerts.NWS.FloodWarning;
import com.severeweatheralerts.Alerts.NWS.SevereThunderstormWarning;
import com.severeweatheralerts.Alerts.NWS.SevereThunderstormWatch;
import com.severeweatheralerts.Alerts.NWS.SnowSquallWarning;
import com.severeweatheralerts.Alerts.NWS.SpecialMarineWarning;
import com.severeweatheralerts.Alerts.NWS.TornadoWarning;
import com.severeweatheralerts.Alerts.NWS.TornadoWatch;
import com.severeweatheralerts.TimeFormatters.AbsoluteTimeFormatter;

import java.util.Date;
import java.util.TimeZone;

public class NextUpdate {
  private final Alert alert;
  private final TimeZone timeZone;

  public NextUpdate(Alert alert, TimeZone timeZone) {
    this.alert = alert;
    this.timeZone = timeZone;
  }

  public boolean hasText() {
    if (isCancel() || alertDoesNotProvideNextUpdate()) return false;
    return alert.getExpectedUpdateTime() != null;
  }

  public String getText(Date time) {
    if (hasText()) {
      if (alert.isLikelyLastUpdate()) return "Likely to be the last update";
      return "Next update expected by " + new AbsoluteTimeFormatter(time, alert.getExpectedUpdateTime(), timeZone).getFormattedString();
    }
    return null;
  }

  private boolean alertDoesNotProvideNextUpdate() {
    return alert.isLikelyLastUpdate() && (
            alert instanceof SevereThunderstormWarning ||
            alert instanceof SevereThunderstormWatch ||
            alert instanceof DustStormWarning ||
            alert instanceof SpecialMarineWarning ||
            alert instanceof SnowSquallWarning ||
            alert instanceof TornadoWatch ||
            alert instanceof TornadoWarning ||
            alert instanceof FloodWarning ||
            alert instanceof FloodAdvisory ||
            alert instanceof FlashFloodWarning);
  }

  private boolean isCancel() {
    return alert.getType() == Alert.Type.CANCEL;
  }
}
