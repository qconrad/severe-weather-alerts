package com.severeweatheralerts;

import java.util.ArrayList;

public class LocationsDao {
  private static ArrayList<Location> locations = new ArrayList<>();
  private LocationsDao() {}
  public static ArrayList<Location> getLocationList() { return locations; }
}
