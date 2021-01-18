package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.AlertListTools.AlertFinder;
import com.severeweatheralerts.Alerts.DefaultAlert;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AlertFinderTests {
  @Test
  public void findAlertByID_EmptyList_NullReturn() {
    AlertFinder als = new AlertFinder(new ArrayList<Alert>());
    assertNull(als.findAlertByID("testID"));
  }

  @Test
  public void findAlertByID_OneAlertIsAdded_ReturnsThatAlert() {
    ArrayList<Alert> alerts = new ArrayList<>();
    DefaultAlert da = new DefaultAlert();
    da.setNwsId("testID");
    alerts.add(da);
    AlertFinder als = new AlertFinder(alerts);
    assertEquals("testID", als.findAlertByID("testID").getNwsId());
  }

  @Test
  public void findAlertByID_IDNotInListProvided_ReturnsNull() {
    ArrayList<Alert> alerts = new ArrayList<>();
    DefaultAlert da = new DefaultAlert();
    da.setNwsId("realID");
    alerts.add(da);
    AlertFinder als = new AlertFinder(alerts);
    assertNull(als.findAlertByID("invalidID"));
  }

  @Test
  public void findAlertByID_TwoAlertsProvided_ReturnCorrectID() {
    ArrayList<Alert> alerts = new ArrayList<>();
    DefaultAlert first = new DefaultAlert();
    DefaultAlert second = new DefaultAlert();
    first.setNwsId("first");
    second.setNwsId("second");
    alerts.add(first);
    alerts.add(second);
    AlertFinder als = new AlertFinder(alerts);
    assertEquals("second", als.findAlertByID("second").getNwsId());
  }
}
