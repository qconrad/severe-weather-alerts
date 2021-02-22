package com.severeweatheralerts.Graphics.GridData;

public class Minimum extends Maximum {
  public Minimum(Parameter parameter) {
    super(parameter);
  }

  @Override
  protected boolean newValue(ForecastTime current, ForecastTime forecastTime) {
    return current == null || forecastTime.getValue() < current.getValue();
  }
}
