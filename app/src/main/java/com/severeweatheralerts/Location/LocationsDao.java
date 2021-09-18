package com.severeweatheralerts.Location;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Preferences.ChannelPreferences;

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

  public static LocationsDao getInstance(Context context) {
//    if (instance == null) instance = new LocationsDao(context);
    return instance;
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

  public synchronized boolean hasLocations() {
    return locations.size() > 0;
  }

  public synchronized void addLocation(String name, double latitude, double longitude) {
    Location location = new Location();
    location.setName(name);
    location.setCoordinate(new GCSCoordinate(latitude, longitude));
    locations.add(location);
    saveLocationsDatabase();
  }

  public synchronized void setDefaultLocation(String name, double latitude, double longitude) {
    if (locations.size() < 1) locations.add(new Location());
    locations.get(0).setName(name);
    locations.get(0).setCoordinate(new GCSCoordinate(latitude, longitude));
    saveLocationsDatabase();
  }

  public synchronized void setDefaultLocationCoordinate(double latitude, double longitude) {
    if (locations.size() < 1) locations.add(new Location());
    locations.get(0).setCoordinate(new GCSCoordinate(latitude, longitude));
    saveLocationsDatabase();
  }

  public synchronized void setName(int locationIndex, String name) {
    if (locationIndex < 0 || locationIndex >= locations.size()) return;
    locations.get(locationIndex).setName(name);
    saveLocationsDatabase();
  }

  public synchronized void setCoordinate(int locationIndex, double latitude, double longitude) {
    if (locationIndex < 0 || locationIndex >= locations.size()) return;
    locations.get(locationIndex).setCoordinate(new GCSCoordinate(latitude, longitude));
    saveLocationsDatabase();
  }

  public synchronized void deleteLocation(int locationIndex) {
    if (locationIndex < 0 || locationIndex >= locations.size()) return;
    locations.remove(locationIndex);
    saveLocationsDatabase();
  }

  public synchronized void updateDefaultLocation(double latitude, double longitude) {
    if (locations.size() < 1) locations.add(new Location());
    locations.get(0).setCoordinate(new GCSCoordinate(latitude, longitude));
    saveLocationsDatabase();
  }

  public synchronized void setChannelPreferences(int locationIndex, ChannelPreferences channelPreferences) {
    locations.get(locationIndex).setChannelPreferences(channelPreferences);
    saveLocationsDatabase();
  }

  public synchronized ArrayList<Alert> getAlerts(int locationIndex) {
    return locations.get(locationIndex).getAlerts();
  }

  public synchronized GCSCoordinate getCoordinate(int locationIndex) {
    return locations.get(locationIndex).getCoordinate();
  }

  public synchronized boolean isNotifying(int locationIndex) {
    return locations.get(locationIndex).isNotifying();
  }

  public synchronized void setNotify(int locationIndex, boolean notificationsEnabled) {
    locations.get(locationIndex).setNotify(notificationsEnabled);
  }

  public synchronized ChannelPreferences getChannelPreferences(int locationIndex) {
    return locations.get(locationIndex).getChannelPreferences();
  }

  public synchronized String getName(int locationIndex) {
    return locations.get(locationIndex).getName();
  }

  public synchronized ArrayList<Location> getExtraLocations() {
    return locations;
  }

  public synchronized void setAlerts(int locationIndex, ArrayList<Alert> alerts) {
    messagesAvailable = false;
    locations.get(locationIndex).setAlerts(alerts);
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
