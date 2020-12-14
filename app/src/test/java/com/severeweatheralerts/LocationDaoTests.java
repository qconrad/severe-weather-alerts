package com.severeweatheralerts;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class LocationDaoTests {
  @Test
  public void GetLocation_OneLocationIsAdded_ReturnsThatCount() {
    LocationsDao.getLocationList().add(new Location());
    assertEquals(1, LocationsDao.getLocationList().size());
  }
}
