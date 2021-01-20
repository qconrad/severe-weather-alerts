package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AlertTests {
  @Test
  public void CreateAlertObject_CurrentTimeIsBeforeStart_IsInFuture() {
    DefaultAlert al = new DefaultAlert();
    al.setStartTime(new Date(1577838600000L)); // 01/01/2020 12:30 AM
    Date mockCurrentTime = new Date(1577836800000L); // 01/01/2020 12:00 AM
    assertTrue(al.startsBefore(mockCurrentTime));
  }

  @Test
  public void CreateAlertObject_CurrentAfterIsBeforeStart_IsNotInFuture() {
    DefaultAlert al = new DefaultAlert();
    al.setStartTime(new Date(1577838600000L)); // 01/01/2020 12:30 AM
    Date mockTime = new Date(1577839500000L); // 01/01/2020 12:45 AM
    assertFalse(al.startsBefore(mockTime));
  }

  @Test
  public void CreateAlertObject_TypeIsCancel_IsNotActive() {
    DefaultAlert al = new DefaultAlert();
    al.setType(Alert.Type.CANCEL);
    assertFalse(al.activeAt(new Date(0)));
  }

  @Test
  public void AlertReferenceAddGet_ReferenceIsProvided_CorrectOneReturned() {
    DefaultAlert al = new DefaultAlert();
    DefaultAlert reference = new DefaultAlert();
    al.addReference(reference);
    Alert result = al.getReference(0);
    assertEquals(result.hashCode(), reference.hashCode());
  }

  @Test
  public void NwsIdSetGet_NwsIdGiven_SameIdReturned() {
    DefaultAlert al = new DefaultAlert();
    al.setNwsId("NWS-ID");
    assertEquals("NWS-ID", al.getNwsId());
  }

  @Test
  public void activeAt_isReplaced_NotActive() {
    DefaultAlert da = new DefaultAlert();
    da.setEndTime(new Date(10));
    da.setType(Alert.Type.POST);
    da.activeAt(new Date(0));
    da.setReplacedBy(new DefaultAlert());
    assertFalse(da.activeAt(new Date(0)));
  }

  @Test
  public void getPolygonCount_NothingAdded_Returns0() {
    DefaultAlert da = new DefaultAlert();
    assertEquals(0, da.getPolygonCount());
  }

  @Test
  public void getPolygonCount_OnePolygonAdded_Returns1() {
    DefaultAlert da = new DefaultAlert();
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(0.0, 0.0));
    da.addPolygon(polygon);
    assertEquals(1, da.getPolygonCount());
  }

  @Test
  public void getPolygon_OnePolygonAdded_ReturnsThatPolygon() {
    DefaultAlert da = new DefaultAlert();
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(1.0, 2.0));
    da.addPolygon(polygon);
    assertEquals(1.0, da.getPolygon(0).getCoordinate(0).getX(), 0.0001);
    assertEquals(2.0, da.getPolygon(0).getCoordinate(0).getY(), 0.0001);
  }

  @Test
  public void getPolygon_TwoPolygonsAdded_Get0ReturnsFirstPolygon() {
    DefaultAlert da = new DefaultAlert();
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(1.0, 2.0));
    da.addPolygon(polygon);
    da.addPolygon(new Polygon());
    assertEquals(1.0, da.getPolygon(0).getCoordinate(0).getX(), 0.0001);
    assertEquals(2.0, da.getPolygon(0).getCoordinate(0).getY(), 0.0001);
  }
}
