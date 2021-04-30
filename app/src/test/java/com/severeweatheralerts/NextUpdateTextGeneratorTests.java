package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.Alerts.NWS.DustStormWarning;
import com.severeweatheralerts.Alerts.NWS.FlashFloodWarning;
import com.severeweatheralerts.Alerts.NWS.FloodAdvisory;
import com.severeweatheralerts.Alerts.NWS.FloodWarning;
import com.severeweatheralerts.Alerts.NWS.SevereThunderstormWarning;
import com.severeweatheralerts.Alerts.NWS.SevereThunderstormWatch;
import com.severeweatheralerts.Alerts.NWS.SnowSquallWarning;
import com.severeweatheralerts.Alerts.NWS.SpecialMarineWarning;
import com.severeweatheralerts.Alerts.NWS.TornadoWarning;
import com.severeweatheralerts.Alerts.NWS.TornadoWatch;
import com.severeweatheralerts.Graphics.Polygon.Polygon;
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

  @Test
  public void generateText_SevereThunderstormWarning_DoesNotHaveText() {
    SevereThunderstormWarning alert = new SevereThunderstormWarning();
    NextUpdate nextUpdateTextGenerator = new NextUpdate(alert, TimeZone.getTimeZone("CST"));
    alert.setEndTime(new Date(164000000L));
    alert.setExpectedUpdateTime(new Date(164000000L));
    assertFalse(nextUpdateTextGenerator.hasText());
  }

  @Test
  public void generateText_SevereThunderstormWatch_DoesNotHaveText() {
    SevereThunderstormWatch alert = new SevereThunderstormWatch();
    NextUpdate nextUpdateTextGenerator = new NextUpdate(alert, TimeZone.getTimeZone("CST"));
    alert.setEndTime(new Date(164000000L));
    alert.setExpectedUpdateTime(new Date(164000000L));
    assertFalse(nextUpdateTextGenerator.hasText());
  }

  @Test
  public void generateText_TornadoWarningWithPolygon_DoesNotHaveText() {
    TornadoWarning alert = new TornadoWarning();
    NextUpdate nextUpdateTextGenerator = new NextUpdate(alert, TimeZone.getTimeZone("CST"));
    alert.setEndTime(new Date(164000000L));
    alert.setExpectedUpdateTime(new Date(164000000L));
    assertFalse(nextUpdateTextGenerator.hasText());
  }

  @Test
  public void generateText_TornadoWatch_DoesNotHaveText() {
    TornadoWatch alert = new TornadoWatch();
    NextUpdate nextUpdateTextGenerator = new NextUpdate(alert, TimeZone.getTimeZone("CST"));
    alert.setEndTime(new Date(164000000L));
    alert.setExpectedUpdateTime(new Date(164000000L));
    assertFalse(nextUpdateTextGenerator.hasText());
  }

  @Test
  public void generateText_FlashFloodWarning_DoesNotHaveText() {
    FlashFloodWarning alert = new FlashFloodWarning();
    NextUpdate nextUpdateTextGenerator = new NextUpdate(alert, TimeZone.getTimeZone("CST"));
    alert.setEndTime(new Date(164000000L));
    alert.setExpectedUpdateTime(new Date(164000000L));
    assertFalse(nextUpdateTextGenerator.hasText());
  }

  @Test
  public void generateText_FlashFloodWarningNotLastUpdate_HasText() {
    FlashFloodWarning alert = new FlashFloodWarning();
    NextUpdate nextUpdateTextGenerator = new NextUpdate(alert, TimeZone.getTimeZone("CST"));
    alert.setExpectedUpdateTime(new Date(162000000L));
    alert.setEndTime(new Date(164000000L));
    assertTrue(nextUpdateTextGenerator.hasText());
  }

  @Test
  public void generateText_FloodWarning_DoesNotHaveText() {
    FloodWarning alert = new FloodWarning();
    NextUpdate nextUpdateTextGenerator = new NextUpdate(alert, TimeZone.getTimeZone("CST"));
    alert.setExpectedUpdateTime(new Date(164000000L));
    alert.setEndTime(new Date(164000000L));
    assertFalse(nextUpdateTextGenerator.hasText());
  }

  @Test
  public void generateText_FloodAdvisory_DoesNotHaveText() {
    FloodAdvisory alert = new FloodAdvisory();
    NextUpdate nextUpdateTextGenerator = new NextUpdate(alert, TimeZone.getTimeZone("CST"));
    alert.setExpectedUpdateTime(new Date(164000000L));
    alert.setEndTime(new Date(164000000L));
    assertFalse(nextUpdateTextGenerator.hasText());
  }

  @Test
  public void generateText_DustStormWarning_DoesNotHaveText() {
    DustStormWarning alert = new DustStormWarning();
    NextUpdate nextUpdateTextGenerator = new NextUpdate(alert, TimeZone.getTimeZone("CST"));
    alert.setExpectedUpdateTime(new Date(164000000L));
    alert.setEndTime(new Date(164000000L));
    assertFalse(nextUpdateTextGenerator.hasText());
  }

  @Test
  public void generateText_SpecialMarineWarning_DoesNotHaveText() {
    SpecialMarineWarning alert = new SpecialMarineWarning();
    NextUpdate nextUpdateTextGenerator = new NextUpdate(alert, TimeZone.getTimeZone("CST"));
    alert.setExpectedUpdateTime(new Date(164000000L));
    alert.setEndTime(new Date(164000000L));
    assertFalse(nextUpdateTextGenerator.hasText());
  }

  @Test
  public void generateText_SnowSquallWarning_DoesNotHaveText() {
    SnowSquallWarning alert = new SnowSquallWarning();
    NextUpdate nextUpdateTextGenerator = new NextUpdate(alert, TimeZone.getTimeZone("CST"));
    alert.setExpectedUpdateTime(new Date(164000000L));
    alert.setEndTime(new Date(164000000L));
    assertFalse(nextUpdateTextGenerator.hasText());
  }
}
