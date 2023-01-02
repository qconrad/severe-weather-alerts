package com.severeweatheralerts;

import static junit.framework.TestCase.assertEquals;

import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.GridData.Parameter;
import com.severeweatheralerts.Graphics.Tools.InterpolatedParameterTrim;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class InterpolatedParameterTrimTests {
  // Similar to the ParameterTrim class, but instead of trimming the parameter to the nearest
  // forecast time, interpolates the edges of the trim, adding a new forecast time at the trim edge
  // The interpolated value is not ht in-between value between two forecast times, but rather
  // decreases a value to 0 over the time period between the two forecast times
  // This is useful for computing total accumulations for a given time period
  // Use ParameterTrim to trim to the nearest forecast time

  // Left trim time is before first forecast time, original list is returned
  @Test
  public void leftTrimBeforeFirstForecastTime_ReturnsOriginalList() {
    ArrayList<ForecastTime> forecastTimes = new ArrayList<>();
    forecastTimes.add(new ForecastTime(new Date(10), 0));
    Parameter parameter = new Parameter(forecastTimes);
    InterpolatedParameterTrim interpolatedParameterTrim = new InterpolatedParameterTrim(parameter);
    interpolatedParameterTrim.trimLeft(new Date(5));
    assertEquals(1, interpolatedParameterTrim.trim().getForecastTimes().size());
  }

  // Left trim time is exactly at second forecast time, first forecast time is removed
  @Test
  public void leftTrimAtSecondForecastTime_FirstForecastTimeRemoved() {
    ArrayList<ForecastTime> forecastTimes = new ArrayList<>();
    forecastTimes.add(new ForecastTime(new Date(10), 0));
    forecastTimes.add(new ForecastTime(new Date(20), 0));
    Parameter parameter = new Parameter(forecastTimes);
    InterpolatedParameterTrim interpolatedParameterTrim = new InterpolatedParameterTrim(parameter);
    interpolatedParameterTrim.trimLeft(new Date(20));
    assertEquals(1, interpolatedParameterTrim.trim().getForecastTimes().size());
  }

  // 3 forecast times, left trim time is second time, first time is removed
  @Test
  public void leftTrimAtSecondForecastTime_FirstForecastTimeRemoved_3ForecastTimes() {
    ArrayList<ForecastTime> forecastTimes = new ArrayList<>();
    forecastTimes.add(new ForecastTime(new Date(10), 0));
    forecastTimes.add(new ForecastTime(new Date(20), 0));
    forecastTimes.add(new ForecastTime(new Date(30), 0));
    Parameter parameter = new Parameter(forecastTimes);
    InterpolatedParameterTrim interpolatedParameterTrim = new InterpolatedParameterTrim(parameter);
    interpolatedParameterTrim.trimLeft(new Date(20));
    assertEquals(2, interpolatedParameterTrim.trim().getForecastTimes().size());
  }

  // 2 forecast times trim time is between the two,
  // first forecast is removed, a new forecast time is added at the trim time
  // with the interpolated value
  @Test
  public void leftTrimBetweenForecastTimes_FirstForecastTimeRemoved_NewForecastTimeAdded() {
    ArrayList<ForecastTime> forecastTimes = new ArrayList<>();
    forecastTimes.add(new ForecastTime(new Date(10), 0));
    forecastTimes.add(new ForecastTime(new Date(20), 10));
    Parameter parameter = new Parameter(forecastTimes);
    InterpolatedParameterTrim interpolatedParameterTrim = new InterpolatedParameterTrim(parameter);
    interpolatedParameterTrim.trimLeft(new Date(15));
    assertEquals(0.0, interpolatedParameterTrim.trim().get(0).getValue());
  }

  // Interpolated between different values
  @Test
  public void leftTrimBetweenForecastTimes_FirstForecastTimeRemoved_NewForecastTimeAdded_DifferentValues() {
    ArrayList<ForecastTime> forecastTimes = new ArrayList<>();
    forecastTimes.add(new ForecastTime(new Date(10), 10));
    forecastTimes.add(new ForecastTime(new Date(20), 20));
    Parameter parameter = new Parameter(forecastTimes);
    InterpolatedParameterTrim interpolatedParameterTrim = new InterpolatedParameterTrim(parameter);
    interpolatedParameterTrim.trimLeft(new Date(15));
    assertEquals(5.0, interpolatedParameterTrim.trim().get(0).getValue());
  }

  // Different dates
  @Test
  public void leftTrimBetweenForecastTimes_FirstForecastTimeRemoved_NewForecastTimeAdded_DifferentDates() {
    ArrayList<ForecastTime> forecastTimes = new ArrayList<>();
    forecastTimes.add(new ForecastTime(new Date(20), 10));
    forecastTimes.add(new ForecastTime(new Date(30), 20));
    Parameter parameter = new Parameter(forecastTimes);
    InterpolatedParameterTrim interpolatedParameterTrim = new InterpolatedParameterTrim(parameter);
    interpolatedParameterTrim.trimLeft(new Date(25));
    assertEquals(5.0, interpolatedParameterTrim.trim().get(0).getValue());
  }

  // Time is between second and third forecast time, first two forecast times are removed
  // and a new forecast time is added at the trim time with the interpolated value
  @Test
  public void leftTrimBetweenForecastTimes_FirstTwoForecastTimesRemoved_NewForecastTimeAdded() {
    ArrayList<ForecastTime> forecastTimes = new ArrayList<>();
    forecastTimes.add(new ForecastTime(new Date(10), 0));
    forecastTimes.add(new ForecastTime(new Date(20), 10));
    forecastTimes.add(new ForecastTime(new Date(30), 20));
    Parameter parameter = new Parameter(forecastTimes);
    InterpolatedParameterTrim interpolatedParameterTrim = new InterpolatedParameterTrim(parameter);
    interpolatedParameterTrim.trimLeft(new Date(25));
    assertEquals(5.0, interpolatedParameterTrim.trim().get(0).getValue());
  }

  // left trim time is after last forecast time, all forecast times are removed
  @Test
  public void leftTrimAfterLastForecastTime_AllForecastTimesRemoved() {
    ArrayList<ForecastTime> forecastTimes = new ArrayList<>();
    forecastTimes.add(new ForecastTime(new Date(10), 0));
    forecastTimes.add(new ForecastTime(new Date(20), 10));
    forecastTimes.add(new ForecastTime(new Date(30), 20));
    Parameter parameter = new Parameter(forecastTimes);
    InterpolatedParameterTrim interpolatedParameterTrim = new InterpolatedParameterTrim(parameter);
    interpolatedParameterTrim.trimLeft(new Date(35));
    assertEquals(0, interpolatedParameterTrim.trim().getForecastTimes().size());
  }

  // Interpolated value is 75% between first and second forecast time
  @Test
  public void leftTrimBetweenForecastTimes_FirstTwoForecastTimesRemoved_NewForecastTimeAdded_75Percent() {
    ArrayList<ForecastTime> forecastTimes = new ArrayList<>();
    forecastTimes.add(new ForecastTime(new Date(0), 10));
    forecastTimes.add(new ForecastTime(new Date(10), 10));
    Parameter parameter = new Parameter(forecastTimes);
    InterpolatedParameterTrim interpolatedParameterTrim = new InterpolatedParameterTrim(parameter);
    interpolatedParameterTrim.trimLeft(new Date(7));
    assertEquals(3.0, interpolatedParameterTrim.trim().get(0).getValue());
  }

  // Trim left returns itself for chaining
  @Test
  public void leftTrimReturnsSelf() {
    ArrayList<ForecastTime> forecastTimes = new ArrayList<>();
    forecastTimes.add(new ForecastTime(new Date(0), 0));
    forecastTimes.add(new ForecastTime(new Date(10), 10));
    Parameter parameter = new Parameter(forecastTimes);
    InterpolatedParameterTrim interpolatedParameterTrim = new InterpolatedParameterTrim(parameter);
    assertEquals(interpolatedParameterTrim, interpolatedParameterTrim.trimLeft(new Date(0)));
  }

  // Trim right between forecast times
  @Test
  public void rightTrimBetweenForecastTimes() {
    ArrayList<ForecastTime> forecastTimes = new ArrayList<>();
    forecastTimes.add(new ForecastTime(new Date(10), 0));
    forecastTimes.add(new ForecastTime(new Date(20), 10));
    forecastTimes.add(new ForecastTime(new Date(30), 20));
    Parameter parameter = new Parameter(forecastTimes);
    InterpolatedParameterTrim interpolatedParameterTrim = new InterpolatedParameterTrim(parameter);
    interpolatedParameterTrim.trimRight(new Date(25));
    assertEquals(10.0, interpolatedParameterTrim.trim().get(2).getValue());
  }

  // Left and right trim
  @Test
  public void leftAndRightTrim() {
    ArrayList<ForecastTime> forecastTimes = new ArrayList<>();
    forecastTimes.add(new ForecastTime(new Date(10), 0));
    forecastTimes.add(new ForecastTime(new Date(20), 10));
    forecastTimes.add(new ForecastTime(new Date(30), 20));
    forecastTimes.add(new ForecastTime(new Date(40), 30));
    forecastTimes.add(new ForecastTime(new Date(50), 40));
    Parameter parameter = new Parameter(forecastTimes);
    InterpolatedParameterTrim interpolatedParameterTrim = new InterpolatedParameterTrim(parameter);
    interpolatedParameterTrim.trimLeft(new Date(25));
    interpolatedParameterTrim.trimRight(new Date(45));
    assertEquals(5.0, interpolatedParameterTrim.trim().get(0).getValue());
    assertEquals(20.0, interpolatedParameterTrim.trim().get(3).getValue());
  }
}
