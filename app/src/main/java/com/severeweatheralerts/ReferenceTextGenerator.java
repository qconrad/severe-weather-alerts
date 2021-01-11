package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.TimeFormatters.RelativeTimeFormatter;

import java.util.Date;

public class ReferenceTextGenerator {
  private final Alert alert;

  public ReferenceTextGenerator(Alert alert) {
    this.alert = alert;
  }

  public String getText(Date date) {
    String time = new RelativeTimeFormatter(date, alert.getSentTime()).getFormattedString();
    if (alert.getType() == Alert.Type.POST)
      return "Post: " + time + " ago";
    else
      return "Update: " + time + " ago";
  }
}
