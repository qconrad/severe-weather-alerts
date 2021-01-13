package com.severeweatheralerts.TextGeneraters.Time;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.TimeFormatters.RelativeTimeFormatter;

import java.util.Date;

public class ReferenceTime extends AlertTime{
  public ReferenceTime(Alert alert, Date date) { super(alert, date); }

  @Override
  public boolean hasCenterTime() { return true; }

  @Override
  public String getCenterTime() {
    String time = new RelativeTimeFormatter(date, alert.getSentTime()).getFormattedString();
    if (getPost()) return "Original post from " + time + " ago";
    return "Update from " + time +  " ago";
  }

  private boolean getPost() {
    return alert.getType() == Alert.Type.POST;
  }
}
