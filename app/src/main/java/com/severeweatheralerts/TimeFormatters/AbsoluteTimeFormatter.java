package com.severeweatheralerts.TimeFormatters;

import com.severeweatheralerts.TextUtils.DateTimeConverter;

import java.util.Date;

public class AbsoluteTimeFormatter extends TimeFormatter {
  public AbsoluteTimeFormatter(Date first, Date second) {
    super(first, second);
  }

  @Override
  public String getFormattedString() {
    if (getDifference() >= 248400000L) {
      return DateTimeConverter.convertDateToString(second, "h a EEEE");
    }
    if (getDifference() >= 162000000L) {
      return DateTimeConverter.convertDateToString(second, "h a") + " Tomorrow";
    }

    return DateTimeConverter.convertDateToString(second, "h a") + " Today";
  }
}
