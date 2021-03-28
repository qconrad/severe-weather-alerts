package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.GridData.SumCalculator;
import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.GridData.Parameter;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ParamSumCalculatorTests {
  @Test
  public void getSum_EmptyList_SumIsZero() {
    SumCalculator sumCalculator = new SumCalculator(new Parameter(new ArrayList<>()));
    assertEquals(0.0, sumCalculator.getSum(), 0.001);
  }

  @Test
  public void getSum_OneValueGiven_SumIsThatValue() {
    ArrayList<ForecastTime> forecastTimes = new ArrayList<>();
    ForecastTime time = new ForecastTime(null, 1.0);
    forecastTimes.add(time);
    SumCalculator sumCalculator = new SumCalculator(new Parameter(forecastTimes));
    assertEquals(1.0, sumCalculator.getSum(), 0.001);
  }

  @Test
  public void getSum_DifferentValueGiven_SumIsThatValue() {
    ArrayList<ForecastTime> forecastTimes = new ArrayList<>();
    ForecastTime time = new ForecastTime(null, 5.0);
    forecastTimes.add(time);
    SumCalculator sumCalculator = new SumCalculator(new Parameter(forecastTimes));
    assertEquals(5.0, sumCalculator.getSum(), 0.001);
  }

  @Test
  public void getSum_TwoValuesGiven_SumIsThatValue() {
    ArrayList<ForecastTime> forecastTimes = new ArrayList<>();
    ForecastTime time0 = new ForecastTime(null, 5.0);
    ForecastTime time1 = new ForecastTime(null, 5.0);
    forecastTimes.add(time0);
    forecastTimes.add(time1);
    SumCalculator sumCalculator = new SumCalculator(new Parameter(forecastTimes));
    assertEquals(10.0, sumCalculator.getSum(), 0.001);
  }
}
