package com.severeweatheralerts;

import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Location.LocationsDao;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LocationDaoTests {
  @Before
  public void resetDao() {
    LocationsDao.clear();
  }

  @Test
  public void GetLocation_OneLocationIsAdded_ReturnsThatCount() {
    LocationsDao.getLocationList().add(new Location());
    assertEquals(1, LocationsDao.getLocationList().size());
  }

  @Test
  public void AddLocation_TwoLocationsAreAddedUsingMethod_SecondOneHasSameHash() {
    LocationsDao.addLocation(new Location());
    Location testLoc = new Location();
    LocationsDao.addLocation(testLoc);
    assertEquals(testLoc.hashCode(), LocationsDao.getLocation(1).hashCode());
  }
}
