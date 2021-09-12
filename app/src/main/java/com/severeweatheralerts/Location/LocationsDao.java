package com.severeweatheralerts.Location;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Adapters.LocationToCoordinateListAdapter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Preferences.ChannelPreferences;

import java.util.ArrayList;

import io.paperdb.Paper;

public class LocationsDao {
  private static boolean messagesAvailable;
  private static NewAlertCallback newAlertCallback;
  private GCSCoordinate lastDefaultSync;
  private ArrayList<Location> locations;
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

  private LocationsDao(Context context) {
    Paper.init(context);
    getLocationsFromFile();
    getLastDefaultSyncFromFile();
  }

  public static boolean hasInstance() {
    return instance != null;
  }

  public static LocationsDao getInstance(Context context) {
    if (instance == null) instance = new LocationsDao(context);
    return instance;
  }

  private synchronized void getLocationsFromFile() {
    locations = Paper.book().read("locations", new ArrayList<>());
  }

  private synchronized void getLastDefaultSyncFromFile() {
    lastDefaultSync = Paper.book().read("lastDefaultSync", new GCSCoordinate(0.0, 0.0));
  }

  private synchronized void saveLocationsToFile() {
    Paper.book().write("locations", locations);
  }

  private synchronized void saveLastDefaultSyncToFile() {
    Paper.book().write("lastDefaultSync", lastDefaultSync);
  }

  public synchronized GCSCoordinate getLastDefaultSync() {
    return lastDefaultSync;
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
    saveLocationsToFile();
  }

  public synchronized void setDefaultLocation(String name, double latitude, double longitude) {
    if (locations.size() < 1) locations.add(new Location());
    locations.get(0).setName(name);
    locations.get(0).setCoordinate(new GCSCoordinate(latitude, longitude));
    saveLocationsToFile();
  }

  public synchronized void setDefaultLocationCoordinate(double latitude, double longitude) {
    if (locations.size() < 1) locations.add(new Location());
    locations.get(0).setCoordinate(new GCSCoordinate(latitude, longitude));
    saveLocationsToFile();
  }

  public synchronized void setName(int locationIndex, String name) {
    if (locationIndex < 0 || locationIndex >= locations.size()) return;
    locations.get(locationIndex).setName(name);
    saveLocationsToFile();
  }

  public synchronized void setCoordinate(int locationIndex, double latitude, double longitude) {
    if (locationIndex < 0 || locationIndex >= locations.size()) return;
    locations.get(locationIndex).setCoordinate(new GCSCoordinate(latitude, longitude));
    saveLocationsToFile();
  }

  public synchronized void deleteLocation(int locationIndex) {
    if (locationIndex < 0 || locationIndex >= locations.size()) return;
    locations.remove(locationIndex);
    saveLocationsToFile();
  }

  public synchronized void updateDefaultLocation(double latitude, double longitude) {
    if (locations.size() < 1) locations.add(new Location());
    locations.get(0).setCoordinate(new GCSCoordinate(latitude, longitude));
    saveLocationsToFile();
  }

  public synchronized void setChannelPreferences(int locationIndex, ChannelPreferences channelPreferences) {
    locations.get(locationIndex).setChannelPreferences(channelPreferences);
    saveLocationsToFile();
  }

  public synchronized ArrayList<Alert> getAlerts(int locationIndex) {
    return locations.get(locationIndex).getAlerts();
  }

  public synchronized GCSCoordinate getCoordinate(int locationIndex) {
    return locations.get(locationIndex).getCoordinate();
  }

  public synchronized ChannelPreferences getChannelPreferences(int locationIndex) {
    return locations.get(locationIndex).getChannelPreferences();
  }

  public synchronized String getName(int locationIndex) {
    return locations.get(locationIndex).getName();
  }

  public synchronized ArrayList<Location> getLocations() {
    return locations;
  }

  public synchronized ArrayList<GCSCoordinate> getCoordinateList() {
    return new LocationToCoordinateListAdapter(locations).getCoordinates();
  }

  public synchronized void setAlerts(int locationIndex, ArrayList<Alert> alerts) {
    messagesAvailable = false;
    locations.get(locationIndex).setAlerts(alerts);
  }
}
