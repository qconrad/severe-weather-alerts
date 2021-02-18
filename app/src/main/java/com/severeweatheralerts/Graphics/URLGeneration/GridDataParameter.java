package com.severeweatheralerts.Graphics.URLGeneration;

import java.util.ArrayList;

public class GridDataParameter {
  private final ArrayList<ForecastTime> forecastTimes;

  public GridDataParameter(ArrayList<ForecastTime> forecastTimes) {
    this.forecastTimes = forecastTimes;
  }

  public int getSize() {
    return forecastTimes.size();
  }

  public ForecastTime get(int i) {
    return forecastTimes.get(i);
  }
}
