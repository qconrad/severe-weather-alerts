package com.severeweatheralerts.Graphics.GridData;

import java.util.Date;

public class ForecastTime {
  private final double value;
  private final Date date;

  public ForecastTime(Date date, double value) {
    this.value = value;
    this.date = date;
  }

  public double getValue() {
    return value;
  }

  public Date getDate() {
    return date;
  }
}
