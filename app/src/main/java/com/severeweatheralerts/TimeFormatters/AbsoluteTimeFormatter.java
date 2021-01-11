package com.severeweatheralerts.TimeFormatters;

import com.severeweatheralerts.TextUtils.DateTimeConverter;

import java.util.Date;

public class AbsoluteTimeFormatter extends TimeFormatter {
  public AbsoluteTimeFormatter(Date first, Date second) {
    super(first, second);
  }

  @Override
  public String getFormattedString() {
    return DateTimeConverter.convertDateToString(second, "h a") + " Today";
  }
}
