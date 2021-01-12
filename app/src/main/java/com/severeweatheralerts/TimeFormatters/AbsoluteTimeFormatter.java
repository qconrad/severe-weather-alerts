package com.severeweatheralerts.TimeFormatters;

import com.severeweatheralerts.TextUtils.DateTimeConverter;

import java.util.Date;

public class AbsoluteTimeFormatter extends TimeFormatter {
  public AbsoluteTimeFormatter(Date first, Date second) {
    super(first, second);
  }

  @Override
  public String getFormattedString() {
    if (greaterThan48Hours()) return DateTimeConverter.convertDateToString(second, "h a EEEE");
    if (greaterThan12Hours()) return DateTimeConverter.convertDateToString(second, "h a") + " Tomorrow";
    return DateTimeConverter.convertDateToString(second, "h a");
  }

  private boolean greaterThan12Hours() {
    return getDifference() >= 43200000L;
  }

  private boolean greaterThan48Hours() {
    return getDifference() >= 172800000L;
  }
}
