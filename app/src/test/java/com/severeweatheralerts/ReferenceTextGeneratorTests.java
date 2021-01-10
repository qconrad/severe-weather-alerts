package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.DefaultAlert;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ReferenceTextGeneratorTests {
  @Test
  public void getText_PostFrom6MinutesAgo_ShowsNameAndTime() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setName("Flood Advisory");
    defaultAlert.setSentTime(new Date(0));
    defaultAlert.setType(Alert.Type.POST);
    ReferenceTextGenerator rtg = new ReferenceTextGenerator(defaultAlert);
    String expected = ("Flood Advisory posted 6 minutes ago");
    assertEquals(expected, rtg.getText(new Date(360000)));
  }

  @Test
  public void getText_NameIsAlertName_ShowsNameAndTime() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setName("Winter Storm Warning");
    defaultAlert.setSentTime(new Date(0));
    defaultAlert.setType(Alert.Type.POST);
    ReferenceTextGenerator rtg = new ReferenceTextGenerator(defaultAlert);
    String expected = ("Winter Storm Warning posted 6 minutes ago");
    assertEquals(expected, rtg.getText(new Date(360000)));
  }

  @Test
  public void getText_Update_JustShowsTime() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setName("Tornado Warning");
    defaultAlert.setSentTime(new Date(0));
    defaultAlert.setType(Alert.Type.UPDATE);
    ReferenceTextGenerator rtg = new ReferenceTextGenerator(defaultAlert);
    String expected = ("Updated 6 minutes ago");
    assertEquals(expected, rtg.getText(new Date(360000)));
  }

  @Test
  public void getText_Update_TimeIsCorrect() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setName("Tornado Warning");
    defaultAlert.setSentTime(new Date(0));
    defaultAlert.setType(Alert.Type.UPDATE);
    ReferenceTextGenerator rtg = new ReferenceTextGenerator(defaultAlert);
    String expected = ("Updated 14 minutes ago");
    assertEquals(expected, rtg.getText(new Date(840000)));
  }
}
