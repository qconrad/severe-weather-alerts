package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.TextGeneraters.Reference;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ReferenceTextGeneratorTests {
  @Test
  public void getText_PostFrom6MinutesAgo_ShowsNameAndTime() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setSentTime(new Date(0));
    defaultAlert.setType(Alert.Type.POST);
    Reference rtg = new Reference(defaultAlert);
    String expected = ("Post: 6 minutes ago");
    assertEquals(expected, rtg.getText(new Date(360000)));
  }

  @Test
  public void getText_Update_JustShowsTime() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setSentTime(new Date(0));
    defaultAlert.setType(Alert.Type.UPDATE);
    Reference rtg = new Reference(defaultAlert);
    String expected = ("Update: 6 minutes ago");
    assertEquals(expected, rtg.getText(new Date(360000)));
  }

  @Test
  public void getText_Update_TimeIsCorrect() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setSentTime(new Date(0));
    defaultAlert.setType(Alert.Type.UPDATE);
    Reference rtg = new Reference(defaultAlert);
    String expected = ("Update: 14 minutes ago");
    assertEquals(expected, rtg.getText(new Date(840000)));
  }

  @Test
  public void getText_PostWithDifferentTime_TimeIsCorrect() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setSentTime(new Date(0));
    defaultAlert.setType(Alert.Type.POST);
    Reference rtg = new Reference(defaultAlert);
    String expected = ("Post: 14 minutes ago");
    assertEquals(expected, rtg.getText(new Date(840000)));
  }
}
