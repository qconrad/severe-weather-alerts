package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.Alerts.NWS.FlashFloodWatch;
import com.severeweatheralerts.Alerts.NWS.FloodWatch;
import com.severeweatheralerts.Alerts.NWS.FreezeWarning;
import com.severeweatheralerts.Alerts.NWS.FreezeWatch;
import com.severeweatheralerts.Alerts.NWS.FrostAdvisory;
import com.severeweatheralerts.Alerts.NWS.HardFreezeWarning;
import com.severeweatheralerts.Alerts.NWS.HardFreezeWatch;
import com.severeweatheralerts.Alerts.NWS.HighWindWarning;
import com.severeweatheralerts.Alerts.NWS.HighWindWatch;
import com.severeweatheralerts.Alerts.NWS.LakeEffectSnowWarning;
import com.severeweatheralerts.Alerts.NWS.LakeWindAdvisory;
import com.severeweatheralerts.Alerts.NWS.SevereThunderstormWarning;
import com.severeweatheralerts.Alerts.NWS.SevereThunderstormWatch;
import com.severeweatheralerts.Alerts.NWS.TornadoWarning;
import com.severeweatheralerts.Alerts.NWS.TornadoWatch;
import com.severeweatheralerts.Alerts.NWS.WindAdvisory;
import com.severeweatheralerts.Alerts.NWS.WinterStormWarning;
import com.severeweatheralerts.Alerts.NWS.WinterStormWatch;
import com.severeweatheralerts.Alerts.NWS.WinterWeatherAdvisory;
import com.severeweatheralerts.Graphics.Types.AlertArea;
import com.severeweatheralerts.Graphics.Types.Lows;
import com.severeweatheralerts.Graphics.Types.Radar;
import com.severeweatheralerts.Graphics.Types.Rainfall;
import com.severeweatheralerts.Graphics.Types.SPCOutlook;
import com.severeweatheralerts.Graphics.Types.Snowfall;
import com.severeweatheralerts.Graphics.Types.TypeFactory;
import com.severeweatheralerts.Graphics.Types.WindGusts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GraphicTypeFactoryTests {
  @Test
  public void getType_DefaultGiven_ReturnsAlertArea() {
    DefaultAlert defaultAlert = new DefaultAlert();
    TypeFactory graphicFactory = new TypeFactory(defaultAlert);
    assertEquals(graphicFactory.getTypes().get(0).getClass(), AlertArea.class);
  }

  @Test
  public void getType_WinterWeatherAdvisoryGiven_ReturnsSnowfall() {
    WinterWeatherAdvisory winterAlert = new WinterWeatherAdvisory();
    TypeFactory graphicFactory = new TypeFactory(winterAlert);
    assertEquals(graphicFactory.getTypes().get(0).getClass(), Snowfall.class);
  }

  @Test
  public void getType_TypeIsFlashFloodWatch_ReturnsRainfall() {
    FlashFloodWatch rainAlert = new FlashFloodWatch();
    TypeFactory graphicFactory = new TypeFactory(rainAlert);
    assertEquals(graphicFactory.getTypes().get(0).getClass(), Rainfall.class);
  }

  @Test
  public void getType_TypeIsWinterStormWarning_ReturnsSnowfall() {
    WinterStormWarning winterAlert = new WinterStormWarning();
    TypeFactory graphicFactory = new TypeFactory(winterAlert);
    assertEquals(graphicFactory.getTypes().get(0).getClass(), Snowfall.class);
  }

  @Test
  public void getType_TypeIsLakeEffectSnowWarning_ReturnsSnowfall() {
    LakeEffectSnowWarning lakeEffectSnowWarning = new LakeEffectSnowWarning();
    TypeFactory graphicFactory = new TypeFactory(lakeEffectSnowWarning);
    assertEquals(graphicFactory.getTypes().get(0).getClass(), Snowfall.class);
  }

  @Test
  public void getType_TypeIsWindAdvisory_ReturnsWindGusts() {
    WindAdvisory windAlert = new WindAdvisory();
    TypeFactory graphicFactory = new TypeFactory(windAlert);
    assertEquals(graphicFactory.getTypes().get(0).getClass(), WindGusts.class);
  }

  @Test
  public void getType_TypeIsHighWindWarning_ReturnsWindGusts() {
    HighWindWarning windAlert = new HighWindWarning();
    TypeFactory graphicFactory = new TypeFactory(windAlert);
    assertEquals(graphicFactory.getTypes().get(0).getClass(), WindGusts.class);
  }

  @Test
  public void getType_TypeIsFloodWatch_ReturnsWindGusts() {
    FloodWatch rainAlert = new FloodWatch();
    TypeFactory graphicFactory = new TypeFactory(rainAlert);
    assertEquals(graphicFactory.getTypes().get(0).getClass(), Rainfall.class);
  }

  @Test
  public void getType_TornadoWatch_ReturnsSPCOutlook() {
    TornadoWatch tornadoWatch = new TornadoWatch();
    TypeFactory graphicFactory = new TypeFactory(tornadoWatch);
    assertEquals(graphicFactory.getTypes().get(0).getClass(), SPCOutlook.class);
  }

  @Test
  public void getType_SevereThunderstormWatch_ReturnsSPCOutlook() {
    SevereThunderstormWatch severeThunderstormWatch = new SevereThunderstormWatch();
    TypeFactory graphicFactory = new TypeFactory(severeThunderstormWatch);
    assertEquals(graphicFactory.getTypes().get(0).getClass(), SPCOutlook.class);
  }

  @Test
  public void getType_HighWindWatch_ReturnsWindGusts() {
    HighWindWatch highWindWatch = new HighWindWatch();
    TypeFactory graphicFactory = new TypeFactory(highWindWatch);
    assertEquals(graphicFactory.getTypes().get(0).getClass(), WindGusts.class);
  }

  @Test
  public void getType_LakeWindAdvisory_ReturnsWindGusts() {
    LakeWindAdvisory lakeWindAdvisory = new LakeWindAdvisory();
    TypeFactory graphicFactory = new TypeFactory(lakeWindAdvisory);
    assertEquals(graphicFactory.getTypes().get(0).getClass(), WindGusts.class);
  }

  @Test
  public void getType_WinterStormWarningWithWind_ReturnsSnowAndWind() {
    WinterStormWarning winterStormWarning = new WinterStormWarning();
    winterStormWarning.setDescription("WHAT...Heavy snow expected. Total snow accumulations of 1 to 2 feet. Winds gusting as high as 45 mph.");
    TypeFactory graphicFactory = new TypeFactory(winterStormWarning);
    assertEquals(graphicFactory.getTypes().get(1).getClass(), WindGusts.class);
  }

  @Test
  public void getType_WinterStormWarningWithSnow_ReturnsSnow() {
    WinterStormWarning winterStormWarning = new WinterStormWarning();
    winterStormWarning.setDescription("WHAT...Heavy snow expected. Total snow accumulations of 1 to 2 feet.");
    TypeFactory graphicFactory = new TypeFactory(winterStormWarning);
    assertEquals(1, graphicFactory.getTypes().size());
  }

  @Test
  public void getType_FrostAdvisoryProvided_ReturnsLows() {
    FrostAdvisory frostAdvisory = new FrostAdvisory();
    TypeFactory graphicFactory = new TypeFactory(frostAdvisory);
    assertEquals(Lows.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_FreezeWatchProvided_ReturnsLows() {
    FreezeWatch freezeWatch = new FreezeWatch();
    TypeFactory graphicFactory = new TypeFactory(freezeWatch);
    assertEquals(Lows.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_FreezeWarningProvided_ReturnsLows() {
    FreezeWarning freezeWarning = new FreezeWarning();
    TypeFactory graphicFactory = new TypeFactory(freezeWarning);
    assertEquals(Lows.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_WinterStormWatch_ReturnsSnowfall() {
    WinterStormWatch winterStormWatch = new WinterStormWatch();
    TypeFactory graphicFactory = new TypeFactory(winterStormWatch);
    assertEquals(Snowfall.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_HardFreezeWatch_ReturnsLows() {
    HardFreezeWatch hardFreezeWatch = new HardFreezeWatch();
    TypeFactory graphicFactory = new TypeFactory(hardFreezeWatch);
    assertEquals(Lows.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_HardFreezeWarning_ReturnsLows() {
    HardFreezeWarning hardFreezeWarning = new HardFreezeWarning();
    TypeFactory graphicFactory = new TypeFactory(hardFreezeWarning);
    assertEquals(Lows.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_StormWarning_RadarReturned() {
    SevereThunderstormWarning svrWarn = new SevereThunderstormWarning();
    TypeFactory graphicFactory = new TypeFactory(svrWarn);
    assertEquals(Radar.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_TornadoWarning_RadarReturn() {
    TornadoWarning torWarn = new TornadoWarning();
    TypeFactory graphicFactory = new TypeFactory(torWarn);
    assertEquals(Radar.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_TornadoWatch_SecondIsRadar() {
    TornadoWatch torWatch = new TornadoWatch();
    TypeFactory graphicFactory = new TypeFactory(torWatch);
    assertEquals(Radar.class, graphicFactory.getTypes().get(1).getClass());
  }
}