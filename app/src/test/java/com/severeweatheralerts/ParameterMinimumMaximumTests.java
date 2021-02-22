package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.GridData.Maximum;
import com.severeweatheralerts.Graphics.GridData.Minimum;
import com.severeweatheralerts.Graphics.GridData.Parameter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ParameterMinimumMaximumTests {
  @Test
  public void getMaximum_FirstTimeIsMaximum_ReturnsThatTime() {
    ArrayList<ForecastTime> times = new ArrayList<>();
    times.add(new ForecastTime(new Date(0), 5.0));
    times.add(new ForecastTime(new Date(10), 1.0));
    Parameter parameter = new Parameter(times);
    Maximum maximum = new Maximum(parameter);
    assertEquals(0, maximum.get().getDate().getTime());
  }

  @Test
  public void getMaximum_SecondTimeIsMaximum_ReturnsThatTime() {
    ArrayList<ForecastTime> times = new ArrayList<>();
    times.add(new ForecastTime(new Date(0), 1.0));
    times.add(new ForecastTime(new Date(10), 5.0));
    Parameter parameter = new Parameter(times);
    Maximum maximum = new Maximum(parameter);
    assertEquals(10, maximum.get().getDate().getTime());
  }

  @Test
  public void getMaximum_ThreeTimes_SecondTimeIsMaximum_ReturnsThatTime() {
    ArrayList<ForecastTime> times = new ArrayList<>();
    times.add(new ForecastTime(new Date(0), 1.0));
    times.add(new ForecastTime(new Date(5), 5.0));
    times.add(new ForecastTime(new Date(10), 0.0));
    Parameter parameter = new Parameter(times);
    Maximum maximum = new Maximum(parameter);
    assertEquals(5, maximum.get().getDate().getTime());
  }

  @Test
  public void getMinimum_ThreeTimes_SecondTimeIsMaximum_ReturnsThatTime() {
    ArrayList<ForecastTime> times = new ArrayList<>();
    times.add(new ForecastTime(new Date(0), 1.0));
    times.add(new ForecastTime(new Date(5), 5.0));
    times.add(new ForecastTime(new Date(10), 0.0));
    Parameter parameter = new Parameter(times);
    Minimum min = new Minimum(parameter);
    assertEquals(10, min.get().getDate().getTime());
  }
}
