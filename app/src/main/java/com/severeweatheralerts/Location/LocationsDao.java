package com.severeweatheralerts.Location;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Preferences.ChannelPreferences;

import java.util.ArrayList;

public class LocationsDao {
  private static boolean messagesAvailable;
  private static NewAlertCallback newAlertCallback;
  private GCSCoordinate lastDefaultSync;
  private final ArrayList<Location> locations;
  private final LocationDatabase locationDatabase;
  private static LocationsDao instance;

  public LocationsDao(LocationDatabase locationDatabase) {
    locations = locationDatabase.get();
    this.locationDatabase = locationDatabase;
    if (locations.size() <= 0) {
      locations.add(new Location().setChannelPreferences(new ChannelPreferences()));
      saveLocationsDatabase();
    }
  }

//  public static boolean hasInstance() {
//    return instance != null;
//  }

  private synchronized void saveLocationsDatabase() {
    locationDatabase.set(locations);
  }

  public boolean hasExtraLocations() {
    return locations.size() > 1;
  }

  public Location getDefaultLocation() {
    return locations.get(0);
  }

  public void addExtraLocation(Location location) {
    locations.add(location);
    saveLocationsDatabase();
  }

  public void setDefaultLocation(Location location) {
    locations.set(0, location);
    saveLocationsDatabase();
  }

  public int extraLocationCount() {
    return locations.size() - 1;
  }

  public ArrayList<Location> getLocations() {
    return locations;
  }

  public Location getLocation(int locationIndex) {
    return locations.get(locationIndex);
  }

  public void setLocation(int locationIndex, Location location) {
    locations.set(locationIndex, location);
    saveLocationsDatabase();
  }
}
