package com.severeweatheralerts;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Adapters.LocationToCoordinateListAdapter;
import com.severeweatheralerts.Location.Location;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class LocationToCoordinateListAdapterTests {
  @Test
  public void noLocationsGiven_EmptyListReturned() {
    ArrayList<Location> locations = new ArrayList<>();
    LocationToCoordinateListAdapter locationToCoordinateListAdapter = new LocationToCoordinateListAdapter(locations);
    assertEquals(0, locationToCoordinateListAdapter.getCoordinates().size());
  }

  @Test
  public void oneLocationGiven_sizeIsOne() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    location.setCoordinate(new GCSCoordinate(41.0, -87.0));
    locations.add(location);
    LocationToCoordinateListAdapter locationToCoordinateListAdapter = new LocationToCoordinateListAdapter(locations);
    assertEquals(1, locationToCoordinateListAdapter.getCoordinates().size());
  }

  @Test
  public void twoLocationsGiven_sizeIsTwo() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    location.setCoordinate(new GCSCoordinate(41.0, -87.0));
    Location location2 = new Location();
    location2.setCoordinate(new GCSCoordinate(41.0, -87.0));
    locations.add(location);
    locations.add(location2);
    LocationToCoordinateListAdapter locationToCoordinateListAdapter = new LocationToCoordinateListAdapter(locations);
    assertEquals(2, locationToCoordinateListAdapter.getCoordinates().size());
  }

  @Test
  public void returnedLatitudeCorrect() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    location.setCoordinate(new GCSCoordinate(41.0, -87.0));
    locations.add(location);
    LocationToCoordinateListAdapter locationToCoordinateListAdapter = new LocationToCoordinateListAdapter(locations);
    assertEquals(41.0, locationToCoordinateListAdapter.getCoordinates().get(0).getLat(), 0.001);
  }

  @Test
  public void differentReturnLatitudeCorrect() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    location.setCoordinate(new GCSCoordinate(42.0, -87.0));
    locations.add(location);
    LocationToCoordinateListAdapter locationToCoordinateListAdapter = new LocationToCoordinateListAdapter(locations);
    assertEquals(42.0, locationToCoordinateListAdapter.getCoordinates().get(0).getLat(), 0.001);
  }

  @Test
  public void longitudeCorrect() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    location.setCoordinate(new GCSCoordinate(42.0, -87.0));
    locations.add(location);
    LocationToCoordinateListAdapter locationToCoordinateListAdapter = new LocationToCoordinateListAdapter(locations);
    assertEquals(-87.0, locationToCoordinateListAdapter.getCoordinates().get(0).getLong(), 0.001);
  }

  @Test
  public void differentLongitudeCorrect() {
    ArrayList<Location> locations = new ArrayList<>();
    Location location = new Location();
    location.setCoordinate(new GCSCoordinate(42.0, -88.0));
    locations.add(location);
    LocationToCoordinateListAdapter locationToCoordinateListAdapter = new LocationToCoordinateListAdapter(locations);
    assertEquals(-88.0, locationToCoordinateListAdapter.getCoordinates().get(0).getLong(), 0.001);
  }
}
