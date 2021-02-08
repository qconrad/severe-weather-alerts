package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.Alerts.NWS.WinterWeatherAdvisory;
import com.severeweatheralerts.Graphics.AlertArea;
import com.severeweatheralerts.Graphics.ExpectedSnowfall;
import com.severeweatheralerts.Graphics.GraphicFactory;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GraphicFactoryTests {
  @Test
  public void getType_DefaultGiven_ReturnsAlertArea() {
    DefaultAlert defaultAlert = new DefaultAlert();
    GraphicFactory graphicFactory = new GraphicFactory(defaultAlert);
    assertTrue(graphicFactory.getTypes().get(0) instanceof AlertArea);
  }

  @Test
  public void getType_WinterWeatherAdvisoryGiven_ReturnsSnowfall() {
    WinterWeatherAdvisory winterAlert = new WinterWeatherAdvisory();
    GraphicFactory graphicFactory = new GraphicFactory(winterAlert);
    assertTrue(graphicFactory.getTypes().get(0) instanceof ExpectedSnowfall);
  }
}
