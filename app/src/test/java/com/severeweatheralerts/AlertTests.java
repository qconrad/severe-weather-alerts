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
    Date mockCurrentTime = new Date(1577839500000L); // 01/01/2020 12:45 AM
    assertFalse(al.startsBefore(mockCurrentTime));
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
  public void NwsIdGetSet_NwsIdGiven_SameIdReturned() {
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
    da.setReplaced(true);
    assertFalse(da.activeAt(new Date(0)));
  }
}
