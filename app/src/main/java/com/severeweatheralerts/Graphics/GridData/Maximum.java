package com.severeweatheralerts.Graphics.GridData;

public class Maximum {
  private final Parameter paremeter;

  public Maximum(Parameter parameter) {
    this.paremeter = parameter;
  }

  public ForecastTime get() {
    ForecastTime max = null;
    for (ForecastTime forecastTime : paremeter.getForecastTimes())
      if (higherMax(max, forecastTime)) max = forecastTime;
    return max;
  }

  private boolean higherMax(ForecastTime max, ForecastTime forecastTime) {
    return max == null || forecastTime.getValue() > max.getValue();
  }
}
