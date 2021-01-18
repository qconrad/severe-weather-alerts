package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.TextGeneraters.NextUpdate;

import org.junit.Test;

import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class NextUpdateTextGeneratorTests {
  @Test
  public void generateText_IsLastUpdate_hasText() {
    DefaultAlert alert = new DefaultAlert();
    NextUpdate nextUpdateTextGenerator = new NextUpdate(alert, TimeZone.getTimeZone("CST"));
    alert.setEndTime(new Date(0));
    alert.setExpectedUpdateTime(new Date(0));
    assertTrue(nextUpdateTextGenerator.hasText());
  }

  @Test
  public void generateText_DoesNotHaveExpectedUpdateTime_NoText() {
    DefaultAlert alert = new DefaultAlert();
    NextUpdate nextUpdateTextGenerator = new NextUpdate(alert, TimeZone.getTimeZone("CST"));
    assertFalse(nextUpdateTextGenerator.hasText());
  }

  @Test
  public void generateText_DoesNotHaveExpectedUpdateTime_TextIsNull() {
    DefaultAlert alert = new DefaultAlert();
    NextUpdate nextUpdateTextGenerator = new NextUpdate(alert, TimeZone.getTimeZone("CST"));
    assertNull(nextUpdateTextGenerator.getText(new Date(0)));
  }

  @Test
  public void generateText_LastUpdate_TextSaysThat() {
    DefaultAlert alert = new DefaultAlert();
    NextUpdate nextUpdateTextGenerator = new NextUpdate(alert, TimeZone.getTimeZone("CST"));
    alert.setEndTime(new Date(0));
    alert.setExpectedUpdateTime(new Date(0));
    assertEquals("Likely to be the last update", nextUpdateTextGenerator.getText(new Date(0)));
  }

  @Test
  public void generateText_TypeIsCancel_TextIsNull() {
    DefaultAlert alert = new DefaultAlert();
    alert.setType(Alert.Type.CANCEL);
    NextUpdate nextUpdateTextGenerator = new NextUpdate(alert, TimeZone.getTimeZone("CST"));
    alert.setEndTime(new Date(164000000L));
    alert.setExpectedUpdateTime(new Date(162000000L));
    assertNull(nextUpdateTextGenerator.getText(new Date(163000000L)));
  }

  @Test
  public void generateText_TypeIsCancel_DoesNotHaveText() {
    DefaultAlert alert = new DefaultAlert();
    alert.setType(Alert.Type.CANCEL);
    NextUpdate nextUpdateTextGenerator = new NextUpdate(alert, TimeZone.getTimeZone("CST"));
    alert.setEndTime(new Date(164000000L));
    alert.setExpectedUpdateTime(new Date(162000000L));
    assertFalse(nextUpdateTextGenerator.hasText());
  }

  @Test
  public void generateText_ExpectedUpdateTimeProvided_TextSaysThat() {
    DefaultAlert alert = new DefaultAlert();
    NextUpdate nextUpdateTextGenerator = new NextUpdate(alert, TimeZone.getTimeZone("CST"));
    alert.setEndTime(new Date(164000000L));
    alert.setExpectedUpdateTime(new Date(162000000L));
    assertEquals("Next update expected by 3 PM", nextUpdateTextGenerator.getText(new Date(163000000L)));
  }

}
