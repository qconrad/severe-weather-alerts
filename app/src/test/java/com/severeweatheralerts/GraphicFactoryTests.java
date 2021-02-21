package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.Alerts.NWS.FlashFloodWatch;
import com.severeweatheralerts.Alerts.NWS.LakeEffectSnowWarning;
import com.severeweatheralerts.Alerts.NWS.WinterStormWarning;
import com.severeweatheralerts.Alerts.NWS.WinterWeatherAdvisory;
import com.severeweatheralerts.Graphics.Types.AlertArea;
import com.severeweatheralerts.Graphics.Types.ExpectedRainfall;
import com.severeweatheralerts.Graphics.Types.ExpectedSnowfall;
import com.severeweatheralerts.Graphics.GraphicGeneration.GraphicFactory;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class GraphicFactoryTests {
  @Test
  public void getType_DefaultGiven_ReturnsAlertArea() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setType(Alert.Type.POST);
    defaultAlert.setEndTime(new Date(5));
    GraphicFactory graphicFactory = new GraphicFactory(defaultAlert);
    assertEquals(graphicFactory.getTypes().get(0).getClass(), AlertArea.class);
  }

  @Test
  public void getType_WinterWeatherAdvisoryGiven_ReturnsSnowfall() {
    WinterWeatherAdvisory winterAlert = new WinterWeatherAdvisory();
    winterAlert.setType(Alert.Type.POST);
    winterAlert.setEndTime(new Date(5));
    GraphicFactory graphicFactory = new GraphicFactory(winterAlert);
    assertEquals(graphicFactory.getTypes().get(0).getClass(), ExpectedSnowfall.class);
  }

  @Test
  public void getType_TypeIsFlashFloodWatch_ReturnsRainfall() {
    FlashFloodWatch rainAlert = new FlashFloodWatch();
    rainAlert.setType(Alert.Type.POST);
    rainAlert.setEndTime(new Date(5));
    GraphicFactory graphicFactory = new GraphicFactory(rainAlert);
    assertEquals(graphicFactory.getTypes().get(0).getClass(), ExpectedRainfall.class);
  }

  @Test
  public void getType_TypeIsWinterStormWarning_ReturnsSnowfall() {
    WinterStormWarning winterAlert = new WinterStormWarning();
    winterAlert.setType(Alert.Type.POST);
    winterAlert.setEndTime(new Date(5));
    GraphicFactory graphicFactory = new GraphicFactory(winterAlert);
    assertEquals(graphicFactory.getTypes().get(0).getClass(), ExpectedSnowfall.class);
  }

  @Test
  public void getType_TypeIsLakeEffectSnowWarning_ReturnsSnowfall() {
    LakeEffectSnowWarning rainAlert = new LakeEffectSnowWarning();
    rainAlert.setType(Alert.Type.POST);
    rainAlert.setEndTime(new Date(5));
    GraphicFactory graphicFactory = new GraphicFactory(rainAlert);
    assertEquals(graphicFactory.getTypes().get(0).getClass(), ExpectedSnowfall.class);
  }
}
