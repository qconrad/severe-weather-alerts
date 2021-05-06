package com.severeweatheralerts.Location;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Adapters.LocationToCoordinateListAdapter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Preferences.ChannelPreferences;

import java.util.ArrayList;

import io.paperdb.Paper;

public class LocationsDao {
  private GCSCoordinate lastDefaultSync;
  private ArrayList<Location> locations;
  private static LocationsDao instance;

  private LocationsDao(Context context) {
    Paper.init(context);
    getLocationsFromFile();
    getLastDefaultSyncFromFile();
  }

  public static LocationsDao getInstance(Context context) {
    if (instance == null) instance = new LocationsDao(context);
    return instance;
  }

  private void getLocationsFromFile() {
    locations = Paper.book().read("locations", new ArrayList<>());
  }

  private void getLastDefaultSyncFromFile() {
    lastDefaultSync = Paper.book().read("lastDefaultSync", new GCSCoordinate(0.0, 0.0));
  }

  private synchronized void saveLocationsToFile() {
    Paper.book().write("locations", locations);
  }

  private synchronized void saveLastDefaultSyncToFile() {
    Paper.book().write("lastDefaultSync", lastDefaultSync);
  }

  public GCSCoordinate getLastDefaultSync() {
    return lastDefaultSync;
  }

  public void setLastDefaultSync(GCSCoordinate coordinate) {
    lastDefaultSync = coordinate;
    saveLastDefaultSyncToFile();
  }

  public boolean hasLocations() {
    return locations.size() > 0;
  }

  public void setDefaultLocation(String name, double latitude, double longitude) {
    if (locations.size() < 1) locations.add(new Location());
    locations.get(0).setName(name);
    locations.get(0).setCoordinate(new GCSCoordinate(latitude, longitude));
    saveLocationsToFile();
  }

  public void setName(int index, String name) {
    locations.get(index).setName(name);
    saveLocationsToFile();
  }

  public void updateDefaultLocation(double latitude, double longitude) {
    if (locations.size() > 0) locations.add(new Location());
    locations.get(0).setCoordinate(new GCSCoordinate(latitude, longitude));
    saveLocationsToFile();
  }

  public void setChannelPreferences(int locationIndex, ChannelPreferences channelPreferences) {
    locations.get(locationIndex).setChannelPreferences(channelPreferences);
    saveLocationsToFile();
  }

  public ArrayList<Alert> getAlerts(int locationIndex) {
    return locations.get(locationIndex).getAlerts();
  }

  public GCSCoordinate getCoordinate(int locationIndex) {
    return locations.get(locationIndex).getCoordinate();
  }

  public ChannelPreferences getChannelPreferences(int locationIndex) {
    return locations.get(locationIndex).getChannelPreferences();
  }

  public String getName(int locationIndex) {
    return locations.get(locationIndex).getName();
  }

  public ArrayList<GCSCoordinate> getCoordinateList() {
    return new LocationToCoordinateListAdapter(locations).getCoordinates();
  }

  public void setAlerts(int locationIndex, ArrayList<Alert> alerts) {
    locations.get(locationIndex).setAlerts(alerts);
  }
}
