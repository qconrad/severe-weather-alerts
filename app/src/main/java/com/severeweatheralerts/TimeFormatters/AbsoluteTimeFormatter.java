package com.severeweatheralerts.TimeFormatters;

import com.severeweatheralerts.TextUtils.DateTimeConverter;

import java.util.Date;
import java.util.TimeZone;

public class AbsoluteTimeFormatter extends TimeFormatter {
  private final TimeZone timeZone;

  public AbsoluteTimeFormatter(Date first, Date second, TimeZone timeZone) {
    super(first, second);
    this.timeZone = timeZone;
  }

  @Override
  public String getFormattedString() {
    return finalize(getTimeString());
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
    return DateTimeConverter.convertDateToString(second, "h a", timeZone);
  }

  private String getDayOfWeek() {
    return DateTimeConverter.convertDateToString(second, "h a EEEE", timeZone);
  }

  private boolean greaterThanHours(int hours) {
    return getDifference() > getMilliseconds(hours);
  }

  private long getMilliseconds(int hours) {
    return (long) hours * 3600000L;
  }
}
