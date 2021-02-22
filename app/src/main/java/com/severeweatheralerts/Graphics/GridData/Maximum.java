package com.severeweatheralerts.Graphics.GridData;

public class Maximum {
  private final Parameter paremeter;

  public Maximum(Parameter parameter) {
    this.paremeter = parameter;
  }

  public ForecastTime get() {
    ForecastTime current = null;
    for (ForecastTime forecastTime : paremeter.getForecastTimes())
      if (newValue(current, forecastTime)) current = forecastTime;
    return current;
  }

  protected boolean newValue(ForecastTime max, ForecastTime forecastTime) {
    return max == null || forecastTime.getValue() > max.getValue();
  }
}
