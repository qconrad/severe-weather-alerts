package com.severeweatheralerts;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AlertTests {
  @Test
  public void CreateAlertObject_CurrentTimeIsBeforeStart_IsInFuture() {
    DefaultAlert al = new DefaultAlert();
    al.setStartTime(new Date(1577838600000L)); // 01/01/2020 12:30 AM
    Date mockCurrentTime = new Date(1577836800000L); // 01/01/2020 12:00 AM
    assertTrue(al.isBeforeStart(mockCurrentTime));
  }

  @Test
  public void CreateAlertObject_CurrentAfterIsBeforeStart_IsNotInFuture() {
    DefaultAlert al = new DefaultAlert();
    al.setStartTime(new Date(1577838600000L)); // 01/01/2020 12:30 AM
    Date mockCurrentTime = new Date(1577839500000L); // 01/01/2020 12:45 AM
    assertFalse(al.isBeforeStart(mockCurrentTime));
  }
}
