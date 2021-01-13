package com.severeweatheralerts.TimeFormatters;

import com.severeweatheralerts.TextUtils.DateTimeConverter;

import java.util.Date;

public class AbsoluteTimeFormatter extends TimeFormatter {
  public AbsoluteTimeFormatter(Date first, Date second) {
    super(first, second);
  }

  @Override
  public String getFormattedString() {
    if (greaterThanHours(23)) return DateTimeConverter.convertDateToString(second, "h a EEEE");
    return DateTimeConverter.convertDateToString(second, "h a");
  }

  private boolean greaterThanHours(int hours) {
    return getDifference() > getMilliseconds(hours);
  }

  private long getMilliseconds(int hours) {
    return (long) hours * 3600000L;
  }
}
