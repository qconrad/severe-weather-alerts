package com.severeweatheralerts.Location;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Adapters.LocationToCoordinateListAdapter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Preferences.ChannelPreferences;

import java.util.ArrayList;

import io.paperdb.Paper;

public class LocationsDao {
  private static ArrayList<Location> locations;
  private LocationsDao() {}

  private static void getLocations() {
    if (locations == null) getLocationsFromFile();
  }

  private static void getLocationsFromFile() {
    locations = Paper.book().read("locations", new ArrayList<>());
  }

  private static void saveToFile() {
    Paper.book().write("locations", locations);
  }

  public static void setDefaultLocation(String name, double latitude, double longitude) {
    getLocations();
    Location location;
    if (locations.size() > 0) location = locations.get(0);
    else location = new Location();
    location.setName(name);
    location.setCoordinate(new GCSCoordinate(latitude, longitude));
    saveToFile();
  }

  public static void setChannelPreferences(int locationIndex, ChannelPreferences channelPreferences) {
    locations.get(locationIndex).setChannelPreferences(channelPreferences);
    saveToFile();
  }

  public static ArrayList<Alert> getAlerts(int locationIndex) {
    getLocations();
    return locations.get(locationIndex).getAlerts();
  }

  public static GCSCoordinate getCoordinate(int locationIndex) {
    getLocations();
    return locations.get(locationIndex).getCoordinate();
  }

  public static ChannelPreferences getChannelPreferences(int locationIndex) {
    getLocations();
    return locations.get(locationIndex).getChannelPreferences();
  }

  public static String getName(int locationIndex) {
    getLocations();
    return locations.get(locationIndex).getName();
  }

  public static ArrayList<GCSCoordinate> getCoordinateList() {
    getLocations();
    return new LocationToCoordinateListAdapter(locations).getCoordinates();
  }

  public static void setAlerts(int locationIndex, ArrayList<Alert> alerts) {
    getLocations();
    locations.get(locationIndex).setAlerts(alerts);
  }
}
