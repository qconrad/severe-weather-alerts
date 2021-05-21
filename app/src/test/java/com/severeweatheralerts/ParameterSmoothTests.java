package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.GridData.Parameter;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class ParameterSmoothTests {
  @Test
  public void emptyList_ReturnsEmptyList() {
    ParameterSmooth parameterSmooth = new ParameterSmooth(new Parameter(new ArrayList<>()), 0.0);
    assertEquals(0, parameterSmooth.constantSmooth().getCount());
  }

  @Test
  public void oneItemInList_ReturnsThatItem() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(null, 0.0));
    ParameterSmooth parameterSmooth = new ParameterSmooth(new Parameter(forecast), 0.0);
    assertEquals(1, parameterSmooth.constantSmooth().getCount());
  }

  @Test
  public void firstItemInListSmoothed() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(null, 1.0));
    forecast.add(new ForecastTime(null, 0.0));
    ParameterSmooth parameterSmooth = new ParameterSmooth(new Parameter(forecast), 1.0);
    assertEquals(0.5, parameterSmooth.constantSmooth().get(0).getValue(), 0.01);
  }

  @Test
  public void threeItems_firstItemInListSmoothed() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(null, 1.0));
    forecast.add(new ForecastTime(null, 0.0));
    forecast.add(new ForecastTime(null, 0.0));
    ParameterSmooth parameterSmooth = new ParameterSmooth(new Parameter(forecast), 1.0);
    assertEquals(0.333, parameterSmooth.constantSmooth().get(0).getValue(), 0.01);
  }

  @Test
  public void secondItemIs1_firstItemInListSmoothed() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(null, 0.0));
    forecast.add(new ForecastTime(null, 1.0));
    forecast.add(new ForecastTime(null, 0.0));
    forecast.add(new ForecastTime(null, 0.0));
    ParameterSmooth parameterSmooth = new ParameterSmooth(new Parameter(forecast), 1.0);
    assertEquals(0.25, parameterSmooth.constantSmooth().get(0).getValue(), 0.01);
  }

  @Test
  public void lowerSmoothAmount_firstItemInListSmoothed() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(null, 0.0));
    forecast.add(new ForecastTime(null, 1.0));
    forecast.add(new ForecastTime(null, 0.0));
    forecast.add(new ForecastTime(null, 0.0));
    ParameterSmooth parameterSmooth = new ParameterSmooth(new Parameter(forecast), 0.25);
    assertEquals(0.5, parameterSmooth.constantSmooth().get(0).getValue(), 0.01);
  }

  @Test
  public void differentValues_firstItemInListSmoothed() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(null, 0.0));
    forecast.add(new ForecastTime(null, 0.0));
    forecast.add(new ForecastTime(null, 1.0));
    forecast.add(new ForecastTime(null, 0.0));
    ParameterSmooth parameterSmooth = new ParameterSmooth(new Parameter(forecast), 0.5);
    assertEquals(0.3333, parameterSmooth.constantSmooth().get(0).getValue(), 0.01);
  }

  @Test
  public void secondItemInListSmoothed() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(null, 0.0));
    forecast.add(new ForecastTime(null, 0.0));
    forecast.add(new ForecastTime(null, 1.0));
    forecast.add(new ForecastTime(null, 0.0));
    ParameterSmooth parameterSmooth = new ParameterSmooth(new Parameter(forecast), 0.25);
    assertEquals(0.3333, parameterSmooth.constantSmooth().get(1).getValue(), 0.01);
  }

  @Test
  public void thirdItemInListSmoothed() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(null, 1.0));
    forecast.add(new ForecastTime(null, 1.0));
    forecast.add(new ForecastTime(null, 0.0));
    forecast.add(new ForecastTime(null, 1.0));
    ParameterSmooth parameterSmooth = new ParameterSmooth(new Parameter(forecast), 0.25);
    assertEquals(0.6666, parameterSmooth.constantSmooth().get(2).getValue(), 0.01);
  }

  @Test
  public void exponentialSmooth_FirstItemNotSmoothed() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(null, 0.0));
    forecast.add(new ForecastTime(null, 1.0));
    forecast.add(new ForecastTime(null, 1.0));
    forecast.add(new ForecastTime(null, 1.0));
    ParameterSmooth parameterSmooth = new ParameterSmooth(new Parameter(forecast), 0.25);
    assertEquals(0.0, parameterSmooth.exponentialSmooth().get(0).getValue(), 0.01);
  }

  @Test
  public void exponentialSmooth_LastItemFullSmooth() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(null, 0.0));
    forecast.add(new ForecastTime(null, 1.0));
    forecast.add(new ForecastTime(null, 1.0));
    forecast.add(new ForecastTime(null, 0.0));
    ParameterSmooth parameterSmooth = new ParameterSmooth(new Parameter(forecast), 0.25);
    assertEquals(0.5, parameterSmooth.exponentialSmooth().get(3).getValue(), 0.01);
  }

  @Test
  public void exponentialSmooth_SecondIndexCorrectValue() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(null, 0.0));
    forecast.add(new ForecastTime(null, 1.0));
    forecast.add(new ForecastTime(null, 1.0));
    forecast.add(new ForecastTime(null, 0.0));
    ParameterSmooth parameterSmooth = new ParameterSmooth(new Parameter(forecast), 1.00);
    assertEquals(0.5, parameterSmooth.exponentialSmooth().get(2).getValue(), 0.01);
  }
}
