package com.severeweatheralerts;

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
  public void getColorAt_AlertWithActiveDateGiven_ReturnsWSWColor() {
    WinterStormWarning al = new WinterStormWarning();
    al.setStartTime(new Date(1577836800000L)); // 01/01/2020 12:00 AM
    al.setEndTime(new Date(1577839500000L)); // 01/01/2020 12:45 AM
    assertEquals(253, al.getColorAt(new Date(1577838600000L))); // 12:30
  }

  @Test
  public void getColorAt_AlertWithInactiveDateGiven_ReturnsExpiredColor() {
    WinterStormWarning al = new WinterStormWarning();
    al.setStartTime(new Date(1577836800000L)); // 01/01/2020 12:00 AM
    al.setEndTime(new Date(1577838600000L)); // 01/01/2020 12:30 AM
    assertEquals(234, al.getColorAt(new Date(1577839500000L))); // 12:40
  }

  @Test
  public void getColorAt_AlertWithInactiveDateBeforeStartGiven_ReturnsExpiredColor() {
    WinterStormWarning al = new WinterStormWarning();
    al.setStartTime(new Date(1577836800000L)); // 01/01/2020 12:00 AM
    al.setEndTime(new Date(1577838600000L)); // 01/01/2020 12:30 AM
    assertEquals(234, al.getColorAt(new Date(1577000000000L)));
  }
}
