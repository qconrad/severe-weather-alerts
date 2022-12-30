package com.severeweatheralerts.Location;

import com.severeweatheralerts.Preferences.ChannelPreferences;

import java.util.ArrayList;

public class LocationsDao {
  private final ArrayList<Location> locations;
  private final LocationDatabase locationDatabase;
  private final LocationDatabase storedLocationDatabase;
  private ArrayList<Location> storedLocations = new ArrayList<>();

  public LocationsDao(LocationDatabase locationDatabase, LocationDatabase storedLocationDatabase) {
    locations = locationDatabase.get();
    storedLocations = storedLocationDatabase.get();
    this.locationDatabase = locationDatabase;
    this.storedLocationDatabase = storedLocationDatabase;
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

  public synchronized void saveStoredLocationsDatabase() {
    storedLocationDatabase.set(storedLocations);
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

  public ChannelPreferences getChannelPreferences(int locationIndex) {
    ChannelPreferences channelPreferences = getLocation(locationIndex).getChannelPreferences();
    if (channelPreferences == null) return getDefaultLocation().getChannelPreferences();
    return channelPreferences;
  }

  public void deleteExtraLocations() {
    while (locations.size() > 1) {
      locations.remove(1);
    }
    saveLocationsDatabase();
  }

  public void storeAwayExtraLocations() {
    for (int i = 1; i < locations.size(); i++)
      storedLocations.add(locations.get(i));
    saveStoredLocationsDatabase();
    deleteExtraLocations();
  }

  public void restoreStoredAwayLocations() {
    if (storedLocations.size() == 0) return;
    locations.addAll(storedLocations);
    saveLocationsDatabase();
    storedLocations.clear();
    saveStoredLocationsDatabase();
  }

  public boolean hasStoredAwayLocations() {
    return storedLocations.size() > 0;
  }
}
