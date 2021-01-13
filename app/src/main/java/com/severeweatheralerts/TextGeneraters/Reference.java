package com.severeweatheralerts.TextGeneraters;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.TimeFormatters.RelativeTimeFormatter;

import java.util.Date;

public class Reference {
  private final Alert alert;

  public Reference(Alert alert) {
    this.alert = alert;
  }

  public String getText(Date date) {
    String time = new RelativeTimeFormatter(date, alert.getSentTime()).getFormattedString();
    if (isPost()) return "Post: " + time + " ago";
    return "Update: " + time + " ago";
  }

  private boolean isPost() {
    return alert.getType() == Alert.Type.POST;
  }
}
