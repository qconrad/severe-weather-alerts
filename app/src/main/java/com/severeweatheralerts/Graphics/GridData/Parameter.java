package com.severeweatheralerts.Graphics.GridData;

import java.util.ArrayList;

public class Parameter {
  private final ArrayList<ForecastTime> forecastTimes;

  public Parameter(ArrayList<ForecastTime> forecastTimes) {
    this.forecastTimes = forecastTimes;
  }

  public int getCount() {
    return forecastTimes.size();
  }

  public ForecastTime get(int i) {
    return forecastTimes.get(i);
  }
}
