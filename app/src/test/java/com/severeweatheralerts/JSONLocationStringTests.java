package com.severeweatheralerts;

import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.UserSync.JSONLocationString;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class JSONLocationStringTests {
  @Test
  public void getString_NoLocationProvided_EmptyArray() {
    JSONLocationString jsonSettingsGenerator = new JSONLocationString(new ArrayList<Location>());
    assertEquals("[[]]", jsonSettingsGenerator.getString());
  }

  @Test
  public void getString_locationProvided_CoordinatesInString() {
    ArrayList<Location> locationList = new ArrayList<>();
    Location loc = new Location();
    loc.setLatitude(40.0);
    loc.setLongitude(-80.0);
    locationList.add(loc);
    JSONLocationString jsonSettingsGenerator = new JSONLocationString(locationList);
    assertEquals("[[40.0,-80.0]]", jsonSettingsGenerator.getString());
  }

  @Test
  public void getString_differentLocationProvided_CoordinatesInString() {
    ArrayList<Location> locationList = new ArrayList<>();
    Location loc = new Location();
    loc.setLatitude(41.0);
    loc.setLongitude(-81.0);
    locationList.add(loc);
    JSONLocationString jsonSettingsGenerator = new JSONLocationString(locationList);
    assertEquals("[[41.0,-81.0]]", jsonSettingsGenerator.getString());
  }

  @Test
  public void getString_TwoLocationsInList_BothCoordinatesInString() {
    ArrayList<Location> locationList = new ArrayList<>();
    Location loc1 = new Location();
    Location loc2 = new Location();
    loc1.setLatitude(41.0);
    loc1.setLongitude(-81.0);
    loc2.setLatitude(40.0);
    loc2.setLongitude(-80.0);
    locationList.add(loc1);
    locationList.add(loc2);
    JSONLocationString jsonSettingsGenerator = new JSONLocationString(locationList);
    assertEquals("[[41.0,-81.0],[40.0,-80.0]]", jsonSettingsGenerator.getString());
  }

  @Test
  public void getString_LongLatitudeIsProvided_TrimmedTo3Digits() {
    ArrayList<Location> locationList = new ArrayList<>();
    Location loc1 = new Location();
    loc1.setLatitude(41.12345678);
    loc1.setLongitude(-81.0);
    locationList.add(loc1);
    JSONLocationString jsonSettingsGenerator = new JSONLocationString(locationList);
    assertEquals("[[41.123,-81.0]]", jsonSettingsGenerator.getString());
  }

  @Test
  public void getString_LongLongitudeIsProvided_TrimmedTo3Digits() {
    ArrayList<Location> locationList = new ArrayList<>();
    Location loc1 = new Location();
    loc1.setLatitude(41.0);
    loc1.setLongitude(-80.12345678);
    locationList.add(loc1);
    JSONLocationString jsonSettingsGenerator = new JSONLocationString(locationList);
    assertEquals("[[41.0,-80.123]]", jsonSettingsGenerator.getString());
  }
}
