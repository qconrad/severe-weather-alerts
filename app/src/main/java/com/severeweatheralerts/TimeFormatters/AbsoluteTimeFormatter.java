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
    if (greaterThanHours(23)) return DateTimeConverter.convertDateToString(second, "h a EEEE", timeZone);
    return DateTimeConverter.convertDateToString(second, "h a", timeZone);
  }

  private boolean greaterThanHours(int hours) {
    return getDifference() > getMilliseconds(hours);
  }

  private long getMilliseconds(int hours) {
    return (long) hours * 3600000L;
  }
}
