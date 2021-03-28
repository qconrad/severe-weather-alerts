package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.GridData.ParameterTrim;
import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.GridData.Parameter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ParameterTrimTests {
  @Test
  public void emptyList_ReturnsEmpty() {
    ParameterTrim dateTrim = new ParameterTrim(new Parameter(new ArrayList<>()));
    assertEquals(0, dateTrim.getTrimmed().getCount());
  }

  @Test
  public void TrimLeft_OneDateRemoved() {
    ArrayList<ForecastTime> times = new ArrayList<>();
    times.add(new ForecastTime(new Date(10), 0.0));
    ParameterTrim dateTrim = new ParameterTrim(new Parameter(times));
    dateTrim.trimLeft(new Date(15));
    assertEquals(0, dateTrim.getTrimmed().getCount());
  }

  @Test
  public void TrimLeft_OneDateNotRemoved() {
    ArrayList<ForecastTime> times = new ArrayList<>();
    times.add(new ForecastTime(new Date(10), 0.0));
    ParameterTrim dateTrim = new ParameterTrim(new Parameter(times));
    dateTrim.trimLeft(new Date(5));
    assertEquals(1, dateTrim.getTrimmed().getCount());
  }

  @Test
  public void TrimLeft_OneRemovedOneKept() {
    ArrayList<ForecastTime> times = new ArrayList<>();
    times.add(new ForecastTime(new Date(10), 0.0));
    times.add(new ForecastTime(new Date(15), 0.0));
    ParameterTrim dateTrim = new ParameterTrim(new Parameter(times));
    dateTrim.trimLeft(new Date(13));
    assertEquals(1, dateTrim.getTrimmed().getCount());
  }

  @Test
  public void TrimRight_Kept() {
    ArrayList<ForecastTime> times = new ArrayList<>();
    times.add(new ForecastTime(new Date(10), 0.0));
    times.add(new ForecastTime(new Date(15), 0.0));
    ParameterTrim dateTrim = new ParameterTrim(new Parameter(times));
    dateTrim.trimRight(new Date(13));
    assertEquals(1, dateTrim.getTrimmed().getCount());
  }

  @Test
  public void TrimLeftAndRight_Correct() {
    ArrayList<ForecastTime> times = new ArrayList<>();
    times.add(new ForecastTime(new Date(5), 0.0));
    times.add(new ForecastTime(new Date(10), 0.0));
    times.add(new ForecastTime(new Date(15), 0.0));
    ParameterTrim dateTrim = new ParameterTrim(new Parameter(times));
    dateTrim.trimLeft(new Date(8));
    dateTrim.trimRight(new Date(13));
    assertEquals(1, dateTrim.getTrimmed().getCount());
  }
}
