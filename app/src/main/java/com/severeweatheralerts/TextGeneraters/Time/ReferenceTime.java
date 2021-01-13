package com.severeweatheralerts.TextGeneraters.Time;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.TimeFormatters.RelativeTimeFormatter;

import java.util.Date;

public class ReferenceTime {
  private final Alert alert;
  private final Date date;

  public ReferenceTime(Alert alert, Date date) {
    this.alert = alert;
    this.date = date;
  }

  public String getText() {
    String time = new RelativeTimeFormatter(date, alert.getSentTime()).getFormattedString();
    if (getPost()) return "Original post from " + time + " ago";
    return "Update from " + time +  " ago";
  }

  private boolean getPost() {
    return alert.getType() == Alert.Type.POST;
  }
}
