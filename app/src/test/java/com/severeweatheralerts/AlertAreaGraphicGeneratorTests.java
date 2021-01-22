package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.Graphics.AlertAreaGenerator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AlertAreaGraphicGeneratorTests {
  @Test
  public void getImage_AlertArea_TitleIsCorrect() {
    DefaultAlert alert = new DefaultAlert();
    AlertAreaGenerator aa = new AlertAreaGenerator(alert);
    assertEquals("Alert Area", aa.getGraphic().getTitle());
  }
}
