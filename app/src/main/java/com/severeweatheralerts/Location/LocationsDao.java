package com.severeweatheralerts.Location;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Adapters.LocationToCoordinateListAdapter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Preferences.ChannelPreferences;

import java.util.ArrayList;

import io.paperdb.Paper;

public class LocationsDao {
  private ArrayList<Location> locations;
  private static LocationsDao instance;

  private LocationsDao(Context context) {
    Paper.init(context);
    getLocationsFromFile();
  }

  public static LocationsDao getInstance(Context context) {
    if (instance == null) instance = new LocationsDao(context);
    return instance;
  }

  private void getLocationsFromFile() {
    locations = Paper.book().read("locations", new ArrayList<>());
  }

  private void saveToFile() {
    Paper.book().write("locations", locations);
  }

  public boolean hasLocations() {
    return locations.size() > 0;
  }

  public void setDefaultLocation(String name, double latitude, double longitude) {
    if (locations.size() < 1) locations.add(new Location());
    locations.get(0).setName(name);
    locations.get(0).setCoordinate(new GCSCoordinate(latitude, longitude));
    saveToFile();
  }

  public void setName(int index, String name) {
    locations.get(index).setName(name);
    saveToFile();
  }

  public void updateDefaultLocation(double latitude, double longitude) {
    if (locations.size() > 0) locations.add(new Location());
    locations.get(0).setCoordinate(new GCSCoordinate(latitude, longitude));
    saveToFile();
  }

  public void setChannelPreferences(int locationIndex, ChannelPreferences channelPreferences) {
    locations.get(locationIndex).setChannelPreferences(channelPreferences);
    saveToFile();
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
