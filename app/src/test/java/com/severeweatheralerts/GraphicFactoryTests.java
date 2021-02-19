package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.Alerts.NWS.FlashFloodWatch;
import com.severeweatheralerts.Alerts.NWS.WinterWeatherAdvisory;
import com.severeweatheralerts.Graphics.Types.AlertArea;
import com.severeweatheralerts.Graphics.Types.ExpectedRainfall;
import com.severeweatheralerts.Graphics.Types.ExpectedSnowfall;
import com.severeweatheralerts.Graphics.GraphicGeneration.GraphicFactory;

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

  @Test
  public void getType_TypeIsFlashFloodWatch_ReturnsRainfall() {
    FlashFloodWatch rainAlert = new FlashFloodWatch();
    GraphicFactory graphicFactory = new GraphicFactory(rainAlert);
    assertTrue(graphicFactory.getTypes().get(0) instanceof ExpectedRainfall);
  }
}
