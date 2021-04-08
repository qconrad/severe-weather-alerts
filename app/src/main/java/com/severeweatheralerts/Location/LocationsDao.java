package com.severeweatheralerts.Location;

import java.util.ArrayList;

import io.paperdb.Paper;

public class LocationsDao {
  private static ArrayList<Location> locations;
  private LocationsDao() {}
  public static ArrayList<Location> getLocationList() {
    getLocations();
    return locations;
  }

  private static void getLocations() {
    if (locations == null) getLocationsFromFile();
  }

  private static void getLocationsFromFile() {
    locations = Paper.book().read("locations", new ArrayList<>());
  }

  private static void saveToFile() {
    Paper.book().write("locations", locations);
  }

  public static void addLocation(Location location) {
    getLocations();
    locations.add(location);
    saveToFile();
  }

  public static Location getLocation(int i) {
    getLocations();
    return locations.get(i);
  }

  public static void setDefaultLocation(Location defaultLocation) {
    getLocations();
    if (locations.size() > 0) locations.set(0, defaultLocation);
    else locations.add(defaultLocation);
    saveToFile();
  }
}
