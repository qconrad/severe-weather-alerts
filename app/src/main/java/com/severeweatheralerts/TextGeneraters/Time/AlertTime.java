package com.severeweatheralerts.TextGeneraters.Time;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.TimeFormatters.RelativeTimeFormatter;

import java.util.Date;

public class AlertTime {
  private final Alert alert;
  private final Date date;

  public AlertTime(Alert alert, Date date) {
    this.alert = alert;
    this.date = date;
  }

  public boolean hasCenterTime() {
    return alert.getType() == Alert.Type.CANCEL;
  }

  public String getCenterTime() {
    return "Cancelled " + getSendTime() + " ago";
  }

  private String getSendTime() {
    return new RelativeTimeFormatter(date, alert.getSentTime()).getFormattedString();
  }

  private String getStartTime() {
    return new RelativeTimeFormatter(date, alert.getStartTime()).getFormattedString();
  }

  private String getEndTime() {
    return new RelativeTimeFormatter(date, alert.getEndTime()).getFormattedString();
  }

  public boolean hasLeftTime() {
    return !hasCenterTime();
  }

  public boolean hasRightTime() {
    return hasLeftTime();
  }

  public String getLeftTime() {
    String verb = "Posted ";
    if (isUpdate()) verb = "Updated ";
    return verb + getSendTime() + " ago";
  }

  private boolean isUpdate() {
    return alert.getType() == Alert.Type.UPDATE;
  }

  public String getRightTime() {
    if (isNotActiveYet()) return "Active in " + getStartTime();
    if (isActive()) return "Active next " + getEndTime();
    return "Expired " + getEndTime() + " ago";
  }

  private boolean isNotActiveYet() {
    return alert.startsBefore(date);
  }

  private boolean isActive() {
    return alert.endsBefore(date);
  }
}
