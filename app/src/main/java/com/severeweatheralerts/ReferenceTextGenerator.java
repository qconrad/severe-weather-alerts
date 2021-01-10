package com.severeweatheralerts;

import java.util.Date;

public class ReferenceTextGenerator {
  private final Alert alert;

  public ReferenceTextGenerator(Alert alert) {
    this.alert = alert;
  }

  public String getText(Date date) {
    String time = new RelativeTimeFormatter(date, alert.getSentTime()).getFormattedString();
    if (isPost())
      return alert.getName() + " posted " + time + " ago";
    else
      return "Updated " + time + " ago";
  }

  private boolean isPost() {
    return alert.getType() == Alert.Type.POST;
  }
}
