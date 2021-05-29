package com.severeweatheralerts.Graphics.GridData;

import java.util.ArrayList;
import java.util.Date;

public class ParameterSmooth {

  private final Parameter parameter;
  private final double smoothAmount;

  public ParameterSmooth(Parameter parameter, double smoothAmount) {
    this.parameter = parameter;
    this.smoothAmount = smoothAmount;
  }

  public Parameter constantSmooth() {
    ArrayList<ForecastTime> times = new ArrayList<>();
    int lookAround = getLookAround();
    for (int i = 0; i < parameter.getCount(); i++) times.add(smooth(i, lookAround));
    return new Parameter(times);
  }

  private int getLookAround() {
    return (int) (smoothAmount * parameter.getCount());
  }

  private ForecastTime smooth(int index, int lookAround) {
    ForecastTime forecastTime = parameter.get(index);
    double sum = 0;
    int lowerBound = Math.max(0, index-lookAround);
    int upperBound = Math.min(parameter.getCount()-1, lookAround+index);
    for (int i = lowerBound; i <= upperBound; i++)
      sum += parameter.get(i).getValue();
    return new ForecastTime(forecastTime.getDate(), sum / (upperBound - lowerBound + 1));
  }

  public Parameter exponentialSmooth() {
    return exponentialSmoothAt(0);
  }

  private Parameter exponentialSmoothAt(int index) {
    ArrayList<ForecastTime> times = new ArrayList<>();
    addUnsmoothedValues(index, times);
    exponentialSmooth(index, times);
    return new Parameter(times);
  }

  private void exponentialSmooth(int index, ArrayList<ForecastTime> times) {
    for (int i = index; i < parameter.getCount(); i++) times.add(smooth(i, getExponentialLookAround(index, i)));
  }

  private void addUnsmoothedValues(int index, ArrayList<ForecastTime> times) {
    for (int i = 0; i < index; i++) times.add(parameter.get(i));
  }

  private int getExponentialLookAround(int index, int i) {
    return (int) (getLookAround() * getMultiplier(index, i));
  }

  private double getMultiplier(int startIndex, int index) {
    return getX(startIndex, index) * getX(startIndex, index);
  }

  private double getX(int startIndex, int index) {
    return (double) index / (parameter.getCount() - startIndex - 1);
  }

  public Parameter exponentialSmoothAfter(Date date) {
    return exponentialSmoothAt(getStartIndex(date));
  }

  private int getStartIndex(Date date) {
    int start = 0;
    for (ForecastTime forecastTime : parameter.getForecastTimes()) {
      if (forecastTime.getDate().after(date)) break;
      start++;
    }
    return start;
  }
}
