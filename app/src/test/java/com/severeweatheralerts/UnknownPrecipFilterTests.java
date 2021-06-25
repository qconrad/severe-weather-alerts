package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.Generators.OneHourPrecipitation.UnknownFilter;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UnknownPrecipFilterTests {
  @Test
  public void sameArrayReturned() {
    ArrayList<Integer> precipTypes = new ArrayList<>();
    precipTypes.add(0);
    assertEquals(0, new UnknownFilter(precipTypes).filter().get(0).intValue());
  }

  @Test
  public void UnknownRemoved() {
    ArrayList<Integer> precipTypes = new ArrayList<>();
    precipTypes.add(0);
    precipTypes.add(5);
    assertEquals(0, new UnknownFilter(precipTypes).filter().get(1).intValue());
  }

  @Test
  public void UnknownDifferentIndexRemoved() {
    ArrayList<Integer> precipTypes = new ArrayList<>();
    precipTypes.add(0);
    precipTypes.add(0);
    precipTypes.add(5);
    assertEquals(0, new UnknownFilter(precipTypes).filter().get(2).intValue());
  }

  @Test
  public void UnknownKeptWithHeavyRain() {
    ArrayList<Integer> precipTypes = new ArrayList<>();
    precipTypes.add(0);
    precipTypes.add(3);
    precipTypes.add(5);
    assertEquals(5, new UnknownFilter(precipTypes).filter().get(2).intValue());
  }

  @Test
  public void UnknownKeptWithHailRain() {
    ArrayList<Integer> precipTypes = new ArrayList<>();
    precipTypes.add(0);
    precipTypes.add(4);
    precipTypes.add(5);
    assertEquals(5, new UnknownFilter(precipTypes).filter().get(2).intValue());
  }

  @Test
  public void AllUnknownKept() {
    ArrayList<Integer> precipTypes = new ArrayList<>();
    precipTypes.add(5);
    precipTypes.add(5);
    precipTypes.add(5);
    assertEquals(5, new UnknownFilter(precipTypes).filter().get(2).intValue());
  }
}
