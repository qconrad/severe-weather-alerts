package com.severeweatheralerts.Location;

import com.severeweatheralerts.Preferences.ChannelPreferences;

import java.util.ArrayList;

public class LocationsDao {
  private final ArrayList<Location> locations;
  private final LocationDatabase locationDatabase;

  public LocationsDao(LocationDatabase locationDatabase) {
    locations = locationDatabase.get();
    this.locationDatabase = locationDatabase;
    if (noLocations()) createDefaultLocation();
  }

  private boolean noLocations() {
    return locations.size() <= 0;
  }

  private void createDefaultLocation() {
    locations.add(new Location().setChannelPreferences(new ChannelPreferences()));
    saveLocationsDatabase();
  }

  protected synchronized void saveLocationsDatabase() {
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

  public void deleteLocation(int locationIndex) {
    locations.remove(locationIndex);
    saveLocationsDatabase();
  }
}
