package com.severeweatheralerts;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.NWS.WinterStormWarning;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AlertColorTests {
  final int EXPIRED_COLOR = Color.parseColor("#515151");
  @Test
  public void getColorAt_AlertWithActiveDateGiven_ReturnsWSWColor() {
    WinterStormWarning al = new WinterStormWarning();
    al.setType(Alert.Type.POST);
    al.setStartTime(new Date(1577836800000L)); // 01/01/2020 12:00 AM
    al.setEndTime(new Date(1577839500000L)); // 01/01/2020 12:45 AM
    assertEquals(Color.parseColor("#FF69B4"), al.getColorAt(new Date(1577838600000L))); // 12:30
  }

  @Test
  public void getColorAt_AlertWithInactiveDateGiven_ReturnsExpiredColor() {
    WinterStormWarning al = new WinterStormWarning();
    al.setType(Alert.Type.UPDATE);
    al.setStartTime(new Date(1577836800000L)); // 01/01/2020 12:00 AM
    al.setEndTime(new Date(1577838600000L)); // 01/01/2020 12:30 AM
    assertEquals(EXPIRED_COLOR, al.getColorAt(new Date(1577839500000L))); // 12:40
  }

  @Test
  public void getColorAt_AlertBeforeStart_ReturnsWSWColor() {
    WinterStormWarning al = new WinterStormWarning();
    al.setType(Alert.Type.POST);
    al.setStartTime(new Date(1577838600000L)); // 01/01/2020 12:30 AM
    al.setEndTime(new Date(1577839500000L)); // 01/01/2020 12:45 AM
    assertEquals(Color.parseColor("#FF69B4"), al.getColorAt(new Date(1577836800000L))); // 12:00
  }
}
