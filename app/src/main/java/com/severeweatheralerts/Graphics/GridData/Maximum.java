package com.severeweatheralerts.Graphics.GridData;

public class Maximum {
  private final Parameter parameter;

  public Maximum(Parameter parameter) {
    this.parameter = parameter;
  }

  public ForecastTime get() {
    ForecastTime current = null;
    for (ForecastTime forecastTime : parameter.getForecastTimes())
      if (newValue(current, forecastTime)) current = forecastTime;
    return current;
  }

  protected boolean newValue(ForecastTime max, ForecastTime forecastTime) {
    return max == null || forecastTime.getValue() > max.getValue();
  }
}
