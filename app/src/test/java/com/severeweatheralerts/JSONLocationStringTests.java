package com.severeweatheralerts;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.UserSync.JSONLocationString;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class JSONLocationStringTests {
  @Test
  public void getString_NoLocationProvided_EmptyArray() {
    JSONLocationString jsonSettingsGenerator = new JSONLocationString(new ArrayList<GCSCoordinate>());
    assertEquals("[[]]", jsonSettingsGenerator.getString());
  }

  @Test
  public void getString_locationProvided_CoordinatesInString() {
    ArrayList<GCSCoordinate> locationList = new ArrayList<>();
    GCSCoordinate loc = new GCSCoordinate(40.0, -80.0);
    locationList.add(loc);
    JSONLocationString jsonSettingsGenerator = new JSONLocationString(locationList);
    assertEquals("[[40.0,-80.0]]", jsonSettingsGenerator.getString());
  }

  @Test
  public void getString_differentLocationProvided_CoordinatesInString() {
    ArrayList<GCSCoordinate> locationList = new ArrayList<>();
    GCSCoordinate loc = new GCSCoordinate(41.0, -81.0);
    locationList.add(loc);
    JSONLocationString jsonSettingsGenerator = new JSONLocationString(locationList);
    assertEquals("[[41.0,-81.0]]", jsonSettingsGenerator.getString());
  }

  @Test
  public void getString_TwoLocationsInList_BothCoordinatesInString() {
    ArrayList<GCSCoordinate> locationList = new ArrayList<>();
    GCSCoordinate loc1 = new GCSCoordinate(41.0, -81.0);
    GCSCoordinate loc2 = new GCSCoordinate(40.0, -80.0);
    locationList.add(loc1);
    locationList.add(loc2);
    JSONLocationString jsonSettingsGenerator = new JSONLocationString(locationList);
    assertEquals("[[41.0,-81.0],[40.0,-80.0]]", jsonSettingsGenerator.getString());
  }

  @Test
  public void getString_LongLatitudeIsProvided_TrimmedTo3Digits() {
    ArrayList<GCSCoordinate> locationList = new ArrayList<>();
    GCSCoordinate loc1 = new GCSCoordinate(41.12345678, -81.0);
    locationList.add(loc1);
    JSONLocationString jsonSettingsGenerator = new JSONLocationString(locationList);
    assertEquals("[[41.123,-81.0]]", jsonSettingsGenerator.getString());
  }

  @Test
  public void getString_LongLongitudeIsProvided_TrimmedTo3Digits() {
    ArrayList<GCSCoordinate> locationList = new ArrayList<>();
    GCSCoordinate loc1 = new GCSCoordinate(41.0, -80.12345678);
    locationList.add(loc1);
    JSONLocationString jsonSettingsGenerator = new JSONLocationString(locationList);
    assertEquals("[[41.0,-80.123]]", jsonSettingsGenerator.getString());
  }
}
