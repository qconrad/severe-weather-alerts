package com.severeweatheralerts;

import java.util.ArrayList;

public class LocationsDao {
  private static ArrayList<Location> locations = new ArrayList<>();
  private LocationsDao() {}
  public static ArrayList<Location> getLocationList() { return locations; }

  public static void addLocation(Location location) {
    locations.add(location);
  }

  public static Location getLocation(int i) {
    return locations.get(i);
  }

  public static void clear() {
    locations = new ArrayList<>();
  }
}
