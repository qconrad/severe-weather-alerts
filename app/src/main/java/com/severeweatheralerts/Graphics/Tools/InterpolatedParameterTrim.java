package com.severeweatheralerts.Graphics.Tools;

import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.GridData.Parameter;
import com.severeweatheralerts.Graphics.GridData.ParameterTrim;

import java.util.ArrayList;
import java.util.Date;

public class InterpolatedParameterTrim extends ParameterTrim {

  public InterpolatedParameterTrim(Parameter parameter) {
    super(parameter);
  }

  public ParameterTrim trimLeft(Date date) {
    this.leftTrimDate = date;
    return this;
  }

  public ParameterTrim trimRight(Date date) {
    this.rightTrimDate = date;
    return this;
  }

  public Parameter trim() {
    return new Parameter(getTrimmedTimes());
  }

  private ArrayList<ForecastTime> getTrimmedTimes() {
    ArrayList<ForecastTime> trimmedTimes = new ArrayList<>();
    for (int i = 0; i < parameter.getCount(); i++) {
      ForecastTime time = parameter.get(i);
      if (shouldInterpolateLeft(i, time)) {
        trimmedTimes.add(new ForecastTime(leftTrimDate, interpolateLeft(leftTrimDate, time, getNext(i))));
      } else if (rightInterpolate(i, time)) {
        trimmedTimes.add(time);
        trimmedTimes.add(new ForecastTime(rightTrimDate, interpolateRight(rightTrimDate, time, getNext(i))));
        break;
      } else if (inBetweenBothTrims(time)) {
        trimmedTimes.add(time);
      }
    }
    return trimmedTimes;
  }

  private boolean rightInterpolate(int i, ForecastTime time) {
    return rightTrimDate != null && rightTrimDate.after(time.getDate()) && hasNext(i) && rightTrimDate.before(getNext(i).getDate());
  }

  private ForecastTime getNext(int i) {
    return parameter.get(i + 1);
  }

  private boolean hasNext(int i) {
    return i + 1 < parameter.getCount();
  }

  private boolean shouldInterpolateLeft(int i, ForecastTime time) {
    return leftTrimDate != null && leftTrimDate.after(time.getDate()) && hasNext(i) && leftTrimDate.before(getNext(i).getDate());
  }

  private boolean inBetweenBothTrims(ForecastTime time) {
    return (shouldTrimLeft() || time.getDate().getTime() >= leftTrimDate.getTime()) &&
           (shouldTrimRight() || time.getDate().getTime() <= rightTrimDate.getTime());
  }

  private boolean shouldTrimRight() {
    return rightTrimDate == null;
  }

  private boolean shouldTrimLeft() {
    return leftTrimDate == null;
  }

  // Subtracts the values between firstTime and date and returns value of date to secondTime
  private double interpolateLeft(Date date, ForecastTime firstTime, ForecastTime secondTime) {
    return firstTime.getValue() - (firstTime.getValue() * getPercent(date, firstTime, secondTime));
  }

  // Subtracts off the values after date and returns value of firstTime to date
  private double interpolateRight(Date date, ForecastTime firstTime, ForecastTime secondTime) {
    return secondTime.getValue() * getPercent(date, firstTime, secondTime);
  }

  private double getPercent(Date date, ForecastTime firstTime, ForecastTime secondTime) {
    return (date.getTime() - firstTime.getDate().getTime()) / (double) (secondTime.getDate().getTime() - firstTime.getDate().getTime());
  }
}
