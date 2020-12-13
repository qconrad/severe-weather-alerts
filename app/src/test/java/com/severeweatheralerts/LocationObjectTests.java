package com.severeweatheralerts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LocationObjectTests {
  private Location makeLocation() {
    return new Location();
  }

  private Alert makeAlert() {
    return new Alert();
  }

  @Test
  public void createLocation_noAlertsAreAdded_noAlertsReturned() {
    Location loc = makeLocation();
    int size = loc.getAlerts().size();
    assertEquals(0, size);
  }

  @Test
  public void addAlert_alertCountIsOne_ThereIsOneAlertReturned() {
    Location loc = makeLocation();
    loc.addAlert(makeAlert());
    int size = loc.getAlerts().size();
    assertEquals(1, size);
  }
}
