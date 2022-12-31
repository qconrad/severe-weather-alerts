package com.severeweatheralerts.TextGeneraters.Time;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.TimeFormatters.RelativeTimeFormatter;

import java.util.Date;

public class AlertTime implements TimeGenerator {
  protected final Alert alert;
  protected final Date date;

  public AlertTime(Alert alert, Date date) {
    this.alert = alert;
    this.date = date;
  }

  public boolean hasCenterTime() {
    return isCancel();
  }

  public String getCenterTime() {
    return "Cancelled " + getSendTime() + " ago";
  }

  private boolean isDiscontinued() {
    return alert.getDiscontinuedAt() != null;
  }

  private String getDiscontinuedTime() {
    return new RelativeTimeFormatter(date, alert.getDiscontinuedAt()).getFormattedString();
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
    return !hasCenterTime();
  }

  public String getLeftTime() {
    String verb = "Posted ";
    if (isUpdate()) verb = "Updated ";
    return verb + getSendTime() + " ago";
  }

  private boolean isUpdate() {
    return alert.getType() == Alert.Type.UPDATE;
  }

  private boolean isCancel() {
    return alert.getType() == Alert.Type.CANCEL;
  }

  public String getRightTime() {
    if (isDiscontinued()) return "Exited area " + getDiscontinuedTime() + " ago";
    if (isNotActiveYet()) return "Active in " + getStartTime();
    if (isActive()) return "Active next " + getEndTime();
    return "Expired " + getEndTime() + " ago";
  }

  private boolean isNotActiveYet() {
    return alert.startsAfter(date);
  }

  private boolean isActive() {
    return alert.activeAt(date);
  }
}
