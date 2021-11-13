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
    LocationsDao locationsDao = new LocationsDao(new MockDatabase());
    assertFalse(locationsDao.hasExtraLocations());
  }

  @Test
  public void returnsDefaultLocation() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase());
    assertNotNull(locationsDao.getDefaultLocation());
  }

  @Test
  public void returnsCorrectDefaultLocation() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    location.setName("Test Location");
    locations.add(location);
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(locations));
    assertEquals("Test Location", locationsDao.getDefaultLocation().getName());
  }

  @Test
  public void hasAnExtraLocation() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    locations.add(location);
    Location location2 = new Location();
    locations.add(location2);
    LocationsDao locationsDao = new LocationsDao(new MockDatabase(locations));
    assertTrue(locationsDao.hasExtraLocations());
  }

  @Test
  public void addExtraLocation() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase());
    locationsDao.addExtraLocation(new Location());
    assertTrue(locationsDao.hasExtraLocations());
  }

  @Test
  public void defaultLocationAddedToDatabaseOnConstruction() {
    MockDatabase mockDatabase = new MockDatabase();
    new LocationsDao(mockDatabase);
    assertEquals(1, mockDatabase.get().size());
  }

  @Test
  public void addedLocationAddedToDatabase() {
    MockDatabase mockDatabase = new MockDatabase();
    LocationsDao locationsDao = new LocationsDao(mockDatabase);
    locationsDao.addExtraLocation(new Location());
    assertEquals(2, mockDatabase.get().size());
  }

  @Test
  public void defaultLocationAddedToDatabase() {
    MockDatabase mockDatabase = new MockDatabase();
    LocationsDao locationsDao = new LocationsDao(mockDatabase);
    Location location = new Location();
    location.setName("Test Location");
    locationsDao.setDefaultLocation(location);
    assertEquals("Test Location", mockDatabase.get().get(0).getName());
  }

  @Test
  public void getExtraLocationCount() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase());
    locationsDao.addExtraLocation(new Location());
    locationsDao.addExtraLocation(new Location());
    assertEquals(2, locationsDao.extraLocationCount());
  }

  @Test
  public void getDifferentExtraLocationCount() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase());
    locationsDao.addExtraLocation(new Location());
    locationsDao.addExtraLocation(new Location());
    locationsDao.addExtraLocation(new Location());
    assertEquals(3, locationsDao.extraLocationCount());
  }

  @Test
  public void getLocationByIndex() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase());
    Location location = new Location();
    location.setName("Test");
    locationsDao.addExtraLocation(location);
    assertEquals("Test", locationsDao.getLocation(1).getName());
  }

  @Test
  public void setLocationByIndex() {
    MockDatabase mockDatabase = new MockDatabase();
    LocationsDao locationsDao = new LocationsDao(mockDatabase);
    Location location = new Location();
    location.setName("Test");
    locationsDao.setLocation(0, location);
    assertEquals("Test", mockDatabase.get().get(0).getName());
  }

  @Test
  public void locationCountCorrect() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase());
    locationsDao.addExtraLocation(new Location());
    assertEquals(2, locationsDao.getLocations().size());
  }

  @Test
  public void defaultLocationHasChannelPreferences() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase());
    assertNotNull(locationsDao.getDefaultLocation().getChannelPreferences());
  }

  @Test
  public void deleteLocation() {
    ArrayList<Location> locations = new ArrayList<>();
    locations.add(new Location());
    locations.add(new Location());
    MockDatabase mockDatabase = new MockDatabase(locations);
    LocationsDao locationsDao = new LocationsDao(mockDatabase);
    locationsDao.deleteLocation(1);
    assertEquals(1, mockDatabase.get().size());
  }

  @Test
  public void getChannelPreferences_HasChannelPreferences_ReturnsChannelPreferences() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase());
    Location location = new Location();
    ChannelPreferences prefs = new ChannelPreferences();
    prefs.setChannel("Tornado Warning", Alert.Type.POST, Channel.LOW);
    location.setChannelPreferences(prefs);
    locationsDao.addExtraLocation(location);
    assertEquals(Channel.LOW, locationsDao.getChannelPreferences(1).getChannel("Tornado Warning", Alert.Type.POST));
  }

  @Test
  public void getChannelPreferences_Index2_ReturnsChannelPreferences() {
    LocationsDao locationsDao = new LocationsDao(new MockDatabase());
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
    LocationsDao locationsDao = new LocationsDao(new MockDatabase());
    Location location = new Location();
    ChannelPreferences prefs = new ChannelPreferences();
    prefs.setChannel("Tornado Warning", Alert.Type.POST, Channel.LOW);
    locationsDao.addExtraLocation(location);
    assertEquals(Channel.EXTREME, locationsDao.getChannelPreferences(1).getChannel("Tornado Warning", Alert.Type.POST));
  }
}