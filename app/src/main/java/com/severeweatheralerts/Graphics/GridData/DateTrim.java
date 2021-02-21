package com.severeweatheralerts.Graphics.GridData;

import java.util.ArrayList;
import java.util.Date;

public class DateTrim {
  ArrayList<ForecastTime> times = new ArrayList<>();
  private Parameter parameter;
  private Date rightTrim;
  private Date leftTrim;

  public DateTrim(Parameter parameter) {
    this.parameter = parameter;
  }

  public Parameter getTrimmed() {
    trim();
    return parameter;
  }

  public void trimLeft(Date date) {
    leftTrim = date;
  }

  public void trimRight(Date date) {
    rightTrim = date;
  }

  private void trim() {
    for (ForecastTime time : parameter.getForecastTimes()) checkTime(time);
    parameter = new Parameter(times);
  }

  private void checkTime(ForecastTime time) {
    if (withinLeftTrim(time, leftTrim) && withinRightTrim(time, rightTrim)) times.add(time);
  }

  private boolean withinRightTrim(ForecastTime time, Date right) {
    return rightTrim == null || time.getDate().getTime() < right.getTime();
  }

  private boolean withinLeftTrim(ForecastTime time, Date left) {
    return leftTrim == null || time.getDate().getTime() > left.getTime();
  }
}
