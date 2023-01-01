package com.severeweatheralerts.Graphics.GridData;

import java.util.ArrayList;
import java.util.Date;

public class ParameterTrim {
  ArrayList<ForecastTime> times = new ArrayList<>();
  protected Parameter parameter;
  protected Date rightTrimDate;
  protected Date leftTrimDate;

  public ParameterTrim(Parameter parameter) {
    this.parameter = parameter;
  }

  public Parameter trim() {
    preformTrim();
    return parameter;
  }

  public ParameterTrim trimLeft(Date date) {
    leftTrimDate = date;
    return this;
  }

  public ParameterTrim trimRight(Date date) {
    rightTrimDate = date;
    return this;
  }

  private void preformTrim() {
    for (ForecastTime time : parameter.getForecastTimes()) checkTime(time);
    parameter = new Parameter(times);
  }

  private void checkTime(ForecastTime time) {
    if (withinLeftTrim(time, leftTrimDate) && withinRightTrim(time, rightTrimDate)) times.add(time);
  }

  private boolean withinRightTrim(ForecastTime time, Date right) {
    return rightTrimDate == null || time.getDate().getTime() <= right.getTime();
  }

  private boolean withinLeftTrim(ForecastTime time, Date left) {
    return leftTrimDate == null || time.getDate().getTime() >= left.getTime();
  }
}
