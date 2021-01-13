package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.Location.Location;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class LocationObjectTests {
  private Location makeLocation() {
    return new Location();
  }

  private Alert makeAlert() {
    return new DefaultAlert();
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

  @Test
  public void setAlert_twoAlertsAreAdded_TwoAlertsReturned() {
    Location loc = makeLocation();
    ArrayList<Alert> alertList = new ArrayList<>();
    alertList.add(makeAlert());
    alertList.add(makeAlert());
    loc.setAlerts(alertList);
    int size = loc.getAlerts().size();
    assertEquals(2, size);
  }

  @Test
  public void getLat_LatProvided_CorrectReturn() {
    Location loc = makeLocation();
    loc.setLatitude(41.0);
    assertEquals(41.0, loc.getLatitude(), 0.001);
  }

  @Test
  public void getLat_LongProvided_CorrectReturn() {
    Location loc = makeLocation();
    loc.setLongitude(-87.0);
    assertEquals(-87.0, loc.getLongitude(), 0.001);
  }
}
