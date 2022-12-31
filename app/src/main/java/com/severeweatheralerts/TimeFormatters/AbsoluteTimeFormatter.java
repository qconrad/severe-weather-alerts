package com.severeweatheralerts.TimeFormatters;

import com.severeweatheralerts.TextUtils.DateTimeConverter;

import java.util.Date;
import java.util.TimeZone;

public class AbsoluteTimeFormatter extends TimeFormatter {
  private final TimeZone timeZone;
  private boolean is24Hour;

  public AbsoluteTimeFormatter(Date first, Date second, TimeZone timeZone) {
    super(first, second);
    this.timeZone = timeZone;
  }

  @Override
  public String getFormattedString() {
    return finalize(getTimeString());
  }

  public AbsoluteTimeFormatter setTimeFormat24Hour(boolean is24Hour) {
    this.is24Hour = is24Hour;
    return this;
  }

  protected String finalize(String timeText) {
    return replaceMidnight(replaceNoon(timeText));
  }

  private String replaceMidnight(String timeText) {
    return timeText.replace("12 AM", "midnight");
  }

  private String replaceNoon(String timeText) {
    return timeText.replace("12 PM", "noon");
  }

  private String getTimeString() {
    if (greaterThanHours(23)) return getDayOfWeek();
    return getTimeOnly();
  }

  private String getTimeOnly() {
    if (is24Hour) return DateTimeConverter.convertDateToString(second, "HH:mm", timeZone);
    return DateTimeConverter.convertDateToString(second, "h:mm a", timeZone).replace(":00", "");
  }

  private String getDayOfWeek() {
    if (is24Hour) return DateTimeConverter.convertDateToString(second, "HH:mm EEEE", timeZone);
    return DateTimeConverter.convertDateToString(second, "h:mm a EEEE", timeZone).replace(":00", "");
  }

  private boolean greaterThanHours(int hours) {
    return getDifference() > getMilliseconds(hours);
  }

  private long getMilliseconds(int hours) {
    return (long) hours * 3600000L;
  }
}
