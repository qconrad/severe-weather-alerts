package com.severeweatheralerts;

import android.graphics.Color;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AlertColorTests {
  final int EXPIRED_COLOR = Color.parseColor("#CCCCCC");
  @Test
  public void getColorAt_AlertWithActiveDateGiven_ReturnsWSWColor() {
    WinterStormWarning al = new WinterStormWarning();
    al.setStartTime(new Date(1577836800000L)); // 01/01/2020 12:00 AM
    al.setEndTime(new Date(1577839500000L)); // 01/01/2020 12:45 AM
    assertEquals(Color.parseColor("#FF69B4"), al.getColorAt(new Date(1577838600000L))); // 12:30
  }

  @Test
  public void getColorAt_AlertWithInactiveDateGiven_ReturnsExpiredColor() {
    WinterStormWarning al = new WinterStormWarning();
    al.setStartTime(new Date(1577836800000L)); // 01/01/2020 12:00 AM
    al.setEndTime(new Date(1577838600000L)); // 01/01/2020 12:30 AM
    assertEquals(EXPIRED_COLOR, al.getColorAt(new Date(1577839500000L))); // 12:40
  }

  @Test
  public void getColorAt_AlertWithInactiveDateBeforeStartGiven_ReturnsExpiredColor() {
    WinterStormWarning al = new WinterStormWarning();
    al.setStartTime(new Date(1577836800000L)); // 01/01/2020 12:00 AM
    al.setEndTime(new Date(1577838600000L)); // 01/01/2020 12:30 AM
    assertEquals(EXPIRED_COLOR, al.getColorAt(new Date(1577000000000L)));
  }
}
