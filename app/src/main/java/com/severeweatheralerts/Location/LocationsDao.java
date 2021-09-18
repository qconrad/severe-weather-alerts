package com.severeweatheralerts.Location;

import com.severeweatheralerts.Adapters.GCSCoordinate;

import java.util.ArrayList;

public class LocationsDao {
  private static boolean messagesAvailable;
  private static NewAlertCallback newAlertCallback;
  private GCSCoordinate lastDefaultSync;
  private final ArrayList<Location> locations;
  private final LocationDatabase locationDatabase;
  private static LocationsDao instance;

  public static void messageReceived() {
    if (newAlertCallback != null) newAlertCallback.newAlerts();
    messagesAvailable = true;
  }

  public boolean messagesAvailable() {
    return messagesAvailable;
  }

  public static void onNewAlerts(NewAlertCallback newAlertCallback) {
    LocationsDao.newAlertCallback = newAlertCallback;
  }

  public LocationsDao(LocationDatabase locationDatabase) {
    locations = locationDatabase.get();
    this.locationDatabase = locationDatabase;
    if (locations.size() <= 0) {
      locations.add(new Location());
      saveLocationsDatabase();
    }
//    Paper.init(locationDatabase);
//    getLocationsFromFile();
//    getLastDefaultSyncFromFile();
  }

  public static boolean hasInstance() {
    return instance != null;
  }

  private synchronized void getLocationsFromFile() {
//    locations = Paper.book().read("locations", new ArrayList<>());
  }

  private synchronized void getLastDefaultSyncFromFile() {
//    lastDefaultSync = Paper.book().read("lastDefaultSync", new GCSCoordinate(0.0, 0.0));
  }

  private synchronized void saveLocationsDatabase() {
    locationDatabase.set(locations);
//    Paper.book().write("locations", locations);
  }

  private synchronized void saveLastDefaultSyncToFile() {
//    Paper.book().write("lastDefaultSync", lastDefaultSync);
  }

  public synchronized GCSCoordinate getLastDefaultSync() {
    return new GCSCoordinate(0,0);
  }

  public synchronized void setLastDefaultSync(GCSCoordinate coordinate) {
    lastDefaultSync = coordinate;
    saveLastDefaultSyncToFile();
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
