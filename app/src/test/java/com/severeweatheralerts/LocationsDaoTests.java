package com.severeweatheralerts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.Location.MockDatabase;
import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.Preferences.ChannelPreferences;

import org.junit.Test;

import java.util.ArrayList;

public class LocationsDaoTests {
  @Test
  public void hasNoExtraLocations() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(), new MockDatabase());
    assertFalse(locationsDao.hasExtraLocations());
  }

  @Test
  public void returnsDefaultLocation() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(), new MockDatabase());
    assertNotNull(locationsDao.getDefaultLocation());
  }

  @Test
  public void returnsCorrectDefaultLocation() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    location.setName("Test Location");
    locations.add(location);
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(locations), new MockDatabase());
    assertEquals("Test Location", locationsDao.getDefaultLocation().getName());
  }

  @Test
  public void hasAnExtraLocation() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    locations.add(location);
    Location location2 = new Location();
    locations.add(location2);
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(locations), new MockDatabase());
    assertTrue(locationsDao.hasExtraLocations());
  }

  @Test
  public void addExtraLocation() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(), new MockDatabase());
    locationsDao.addExtraLocation(new Location());
    assertTrue(locationsDao.hasExtraLocations());
  }

  @Test
  public void defaultLocationAddedToDatabaseOnConstruction() {
    MockDatabase mockDatabase = new MockDatabase();
    new LocationsDao(mockDatabase, new MockDatabase());
    assertEquals(1, mockDatabase.get().size());
  }

  @Test
  public void addedLocationAddedToDatabase() {
    MockDatabase mockDatabase = new MockDatabase();
    LocationsDao locationsDao = new LocationsDao(mockDatabase, new MockDatabase());
    locationsDao.addExtraLocation(new Location());
    assertEquals(2, mockDatabase.get().size());
  }

  @Test
  public void defaultLocationAddedToDatabase() {
    MockDatabase mockDatabase = new MockDatabase();
    LocationsDao locationsDao = new LocationsDao(mockDatabase, new MockDatabase());
    Location location = new Location();
    location.setName("Test Location");
    locationsDao.setDefaultLocation(location);
    assertEquals("Test Location", mockDatabase.get().get(0).getName());
  }

  @Test
  public void getExtraLocationCount() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(), new MockDatabase());
    locationsDao.addExtraLocation(new Location());
    locationsDao.addExtraLocation(new Location());
    assertEquals(2, locationsDao.extraLocationCount());
  }

  @Test
  public void getDifferentExtraLocationCount() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(), new MockDatabase());
    locationsDao.addExtraLocation(new Location());
    locationsDao.addExtraLocation(new Location());
    locationsDao.addExtraLocation(new Location());
    assertEquals(3, locationsDao.extraLocationCount());
  }

  @Test
  public void getLocationByIndex() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(), new MockDatabase());
    Location location = new Location();
    location.setName("Test");
    locationsDao.addExtraLocation(location);
    assertEquals("Test", locationsDao.getLocation(1).getName());
  }

  @Test
  public void setLocationByIndex() {
    MockDatabase mockDatabase = new MockDatabase();
    LocationsDao locationsDao = new LocationsDao(mockDatabase, new MockDatabase());
    Location location = new Location();
    location.setName("Test");
    locationsDao.setLocation(0, location);
    assertEquals("Test", mockDatabase.get().get(0).getName());
  }

  @Test
  public void locationCountCorrect() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(), new MockDatabase());
    locationsDao.addExtraLocation(new Location());
    assertEquals(2, locationsDao.getLocations().size());
  }

  @Test
  public void defaultLocationHasChannelPreferences() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(), new MockDatabase());
    assertNotNull(locationsDao.getDefaultLocation().getChannelPreferences());
  }

  @Test
  public void deleteLocation() {
    ArrayList<Location> locations = new ArrayList<>();
    locations.add(new Location());
    locations.add(new Location());
    MockDatabase mockDatabase = new MockDatabase(locations);
    LocationsDao locationsDao = new LocationsDao(mockDatabase, new MockDatabase());
    locationsDao.deleteLocation(1);
    assertEquals(1, mockDatabase.get().size());
  }

  @Test
  public void getChannelPreferences_HasChannelPreferences_ReturnsChannelPreferences() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(), new MockDatabase());
    Location location = new Location();
    ChannelPreferences prefs = new ChannelPreferences();
    prefs.setChannel("Tornado Warning", Alert.Type.POST, Channel.LOW);
    location.setChannelPreferences(prefs);
    locationsDao.addExtraLocation(location);
    assertEquals(Channel.LOW, locationsDao.getChannelPreferences(1).getChannel("Tornado Warning", Alert.Type.POST));
  }

  @Test
  public void getChannelPreferences_Index2_ReturnsChannelPreferences() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(), new MockDatabase());
    Location location1 = new Location();
    ChannelPreferences prefs1 = new ChannelPreferences();
    prefs1.setChannel("Tornado Warning", Alert.Type.POST, Channel.LOW);
    location1.setChannelPreferences(prefs1);
    Location location2 = new Location();
    ChannelPreferences prefs2 = new ChannelPreferences();
    prefs2.setChannel("Tornado Warning", Alert.Type.POST, Channel.HIGH);
    location2.setChannelPreferences(prefs2);
    locationsDao.addExtraLocation(location1);
    locationsDao.addExtraLocation(location2);
    assertEquals(Channel.HIGH, locationsDao.getChannelPreferences(2).getChannel("Tornado Warning", Alert.Type.POST));
  }

  @Test
  public void getChannelPreferences_SecondNull_ReturnsDefaultLocationPreferences() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(), new MockDatabase());
    Location location = new Location();
    ChannelPreferences prefs = new ChannelPreferences();
    prefs.setChannel("Tornado Warning", Alert.Type.POST, Channel.LOW);
    locationsDao.addExtraLocation(location);
    assertEquals(Channel.EXTREME, locationsDao.getChannelPreferences(1).getChannel("Tornado Warning", Alert.Type.POST));
  }

  @Test
  public void deleteExtraLocations_oneExtraLocationAdded_hasNoExtraLocations() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    locations.add(location);
    Location location2 = new Location();
    locations.add(location2);
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(locations), new MockDatabase());
    locationsDao.deleteExtraLocations();
    assertFalse(locationsDao.hasExtraLocations());
  }

  @Test
  public void deleteExtraLocations_twoExtraLocationsAdded_hasNoExtraLocations() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    locations.add(location);
    Location location2 = new Location();
    locations.add(location2);
    Location location3 = new Location();
    locations.add(location3);
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(locations), new MockDatabase());
    locationsDao.deleteExtraLocations();
    assertFalse(locationsDao.hasExtraLocations());
  }

  @Test
  public void deleteExtraLocations_removedFromDatabase() {
    ArrayList<Location> locations = new ArrayList<>();
    MockDatabase mockDatabase = new MockDatabase(locations);;
    Location location = new Location();
    locations.add(location);
    Location location2 = new Location();
    locations.add(location2);
    LocationsDao locationsDao = new LocationsDao(mockDatabase, new MockDatabase());
    locationsDao.deleteExtraLocations();
    assertEquals(1, mockDatabase.get().size());
  }

  @Test
  public void storeAwayExtraLocations_noExtraLocations() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    locations.add(location);
    Location location2 = new Location();
    locations.add(location2);
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(locations), new MockDatabase(new ArrayList<>()));
    locationsDao.storeAwayExtraLocations();
    assertEquals(1, locationsDao.getLocations().size());
  }

  @Test
  public void storeAwayExtraLocations_restoreExtraLocations_hasExtraLocations() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    locations.add(location);
    Location location2 = new Location();
    locations.add(location2);
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(locations), new MockDatabase(new ArrayList<>()));
    locationsDao.storeAwayExtraLocations();
    locationsDao.restoreStoredAwayLocations();
    assertTrue(locationsDao.hasExtraLocations());
  }

  @Test
  public void storeAwayExtraLocations_restoreExtraLocations_hasExtraLocationsInDatabase() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    locations.add(location);
    Location location2 = new Location();
    locations.add(location2);
    MockDatabase mockDatabase = new MockDatabase(locations);
    LocationsDao locationsDao = new LocationsDao(mockDatabase, new MockDatabase(new ArrayList<>()));
    locationsDao.storeAwayExtraLocations();
    locationsDao.restoreStoredAwayLocations();
    assertEquals(2, mockDatabase.get().size());
  }

  @Test
  public void storeAway2ExtraLocationsAndRestore_3Locations() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    locations.add(location);
    Location location2 = new Location();
    locations.add(location2);
    Location location3 = new Location();
    locations.add(location3);
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(locations), new MockDatabase(new ArrayList<>()));
    locationsDao.storeAwayExtraLocations();
    locationsDao.restoreStoredAwayLocations();
    assertEquals(3, locationsDao.getLocations().size());
  }

  @Test
  public void restoreExtraLocationsWithoutStoringAway_noExtraLocations() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    locations.add(location);
    Location location2 = new Location();
    locations.add(location2);
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(locations), new MockDatabase(new ArrayList<>()));
    locationsDao.restoreStoredAwayLocations();
    assertEquals(2, locationsDao.getLocations().size());
  }

  @Test
  public void storeExtraLocation_insideStoredDatabase() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    locations.add(location);
    Location location2 = new Location();
    locations.add(location2);
    ArrayList<Location> storedLocations = new ArrayList<>();
    MockDatabase storedDatabase = new MockDatabase(storedLocations);
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(locations), storedDatabase);
    locationsDao.storeAwayExtraLocations();
    assertEquals(1, storedDatabase.get().size());
  }

  @Test
  public void restoreExtraLocationsTwice_noExtraLocations() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    locations.add(location);
    Location location2 = new Location();
    locations.add(location2);
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(locations), new MockDatabase(new ArrayList<>()));
    locationsDao.storeAwayExtraLocations();
    locationsDao.restoreStoredAwayLocations();
    locationsDao.restoreStoredAwayLocations();
    assertEquals(2, locationsDao.getLocations().size());
  }

  @Test
  public void storeAndRestoreExtraLocations_noExtraLocationsInStoredDatabase() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    locations.add(location);
    Location location2 = new Location();
    locations.add(location2);
    ArrayList<Location> storedLocations = new ArrayList<>();
    MockDatabase storedDatabase = new MockDatabase(storedLocations);
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(locations), storedDatabase);
    locationsDao.storeAwayExtraLocations();
    locationsDao.restoreStoredAwayLocations();
    assertEquals(0, storedDatabase.get().size());
  }

  @Test
  public void restoreExtraLocation_noExtraLocations() {
    ArrayList<Location> locations = new ArrayList<>();
    locations.add(new Location());
    ArrayList<Location> storedLocations = new ArrayList<>();
    storedLocations.add(new Location());
    MockDatabase storedDatabase = new MockDatabase(storedLocations);
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(locations), storedDatabase);
    locationsDao.restoreStoredAwayLocations();
    assertEquals(2, locationsDao.getLocations().size());
  }

  @Test
  public void hasStoredLocations_noStoredLocations() {
    ArrayList<Location> locations = new ArrayList<>();
    locations.add(new Location());
    ArrayList<Location> storedLocations = new ArrayList<>();
    MockDatabase storedDatabase = new MockDatabase(storedLocations);
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(locations), storedDatabase);
    assertFalse(locationsDao.hasStoredAwayLocations());
  }

  @Test
  public void hasStoredLocations_hasStoredLocations() {
    ArrayList<Location> locations = new ArrayList<>();
    locations.add(new Location());
    ArrayList<Location> storedLocations = new ArrayList<>();
    storedLocations.add(new Location());
    MockDatabase storedDatabase = new MockDatabase(storedLocations);
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(locations), storedDatabase);
    assertTrue(locationsDao.hasStoredAwayLocations());
  }

  // getLocation on out of bounds index, returns default location
  @Test
  public void getLocationOutOfBounds_returnsDefaultLocation() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    locations.add(location);
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(locations), new MockDatabase(new ArrayList<>()));
    assertEquals(location, locationsDao.getLocation(1));
  }

  // getLocation on value less than 0, returns default location
  @Test
  public void getLocationNegativeIndex_returnsDefaultLocation() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    locations.add(location);
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(locations), new MockDatabase(new ArrayList<>()));
    assertEquals(location, locationsDao.getLocation(-1));
  }

  // setChannelPreferences, sets channel preferences in database
  @Test
  public void setChannelPreferences_updatesDatabase() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    locations.add(location);
    ChannelPreferences channelPreferences = new ChannelPreferences();
    MockDatabase mockDatabase = new MockDatabase(locations);
    LocationsDao locationsDao = new LocationsDao(mockDatabase, new MockDatabase(new ArrayList<>()));
    mockDatabase.set(new ArrayList<>()); // reset database to make sure it is updated
    channelPreferences.setChannel("Tornado Warning", Alert.Type.POST, Channel.LOW);
    locationsDao.setChannelPreferences(0, channelPreferences);
    assertEquals(Channel.LOW, mockDatabase.get().get(0).getChannelPreferences().getChannel("Tornado Warning", Alert.Type.POST));
  }
}