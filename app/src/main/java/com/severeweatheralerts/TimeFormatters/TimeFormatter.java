package com.severeweatheralerts.TimeFormatters;

import java.util.Date;

public abstract class TimeFormatter {
  public abstract String getFormattedString();
  protected Date first;
  protected Date second;

  public TimeFormatter(Date first, Date second) {
    this.first = first;
    this.second = second;
  }

  protected long getDifference() {
    return Math.abs(second.getTime() - first.getTime());
  }
}
