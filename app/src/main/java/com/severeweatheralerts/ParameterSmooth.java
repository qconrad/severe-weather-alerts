package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.GridData.Parameter;

import java.util.ArrayList;

public class ParameterSmooth {

  private final Parameter parameter;
  private final double smoothAmount;

  public ParameterSmooth(Parameter parameter, double smoothAmount) {
    this.parameter = parameter;
    this.smoothAmount = smoothAmount;
  }

  public Parameter constantSmooth() {
    ArrayList<ForecastTime> times = new ArrayList<>();
    int lookAround = (int) (smoothAmount * parameter.getCount());
    for (int i = 0; i < parameter.getCount(); i++)
      times.add(smooth(i, lookAround));
    return new Parameter(times);
  }

  private ForecastTime smooth(int index, int lookAround) {
    ForecastTime forecastTime = parameter.get(index);
    double sum = 0;
    int lowerBound = Math.max(0, index-lookAround);
    int upperBound = Math.min(parameter.getCount()-1, lookAround+index);
    for (int i = lowerBound; i <= upperBound; i++) {
      sum += parameter.get(i).getValue();
    }
    return new ForecastTime(forecastTime.getDate(), sum / (upperBound - lowerBound + 1));
  }

  public Parameter exponentialSmooth() {
    ArrayList<ForecastTime> times = new ArrayList<>();
    for (int i = 0; i < parameter.getCount(); i++) {
      double x = (double) i / (parameter.getCount()-1);
      double multiplier = x * x;
      int lookAround = (int) Math.round((smoothAmount * parameter.getCount()) * multiplier);
      if (i == 0) times.add(smooth(i, 0));
      else times.add(smooth(i, lookAround));
    }
    return new Parameter(times);
  }
}
