package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.Alerts.MotionVector;
import com.severeweatheralerts.Alerts.NWS.*;
import com.severeweatheralerts.Alerts.TestAlerts.HighPriorityTest;
import com.severeweatheralerts.Alerts.TestAlerts.MediumPriorityTest;
import com.severeweatheralerts.Graphics.Types.*;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class GraphicTypeFactoryTests {
  @Test
  public void getType_DefaultGiven_ReturnsAlertArea() {
    DefaultAlert defaultAlert = new DefaultAlert();
    TypeFactory graphicFactory = new TypeFactory(defaultAlert, new Date(0));
    assertEquals(graphicFactory.getTypes().get(0).getClass(), AlertArea.class);
  }

  @Test
  public void getType_WinterWeatherAdvisoryGiven_ReturnsSnowfall() {
    WinterWeatherAdvisory winterAlert = new WinterWeatherAdvisory();
    TypeFactory graphicFactory = new TypeFactory(winterAlert, new Date(0));
    assertEquals(graphicFactory.getTypes().get(0).getClass(), Snowfall.class);
  }

  @Test
  public void getType_TypeIsFlashFloodWatch_ReturnsRainfall() {
    FlashFloodWatch rainAlert = new FlashFloodWatch();
    TypeFactory graphicFactory = new TypeFactory(rainAlert, new Date(0));
    assertEquals(graphicFactory.getTypes().get(0).getClass(), Rainfall.class);
  }

  @Test
  public void getType_TypeIsWinterStormWarning_ReturnsSnowfall() {
    WinterStormWarning winterAlert = new WinterStormWarning();
    TypeFactory graphicFactory = new TypeFactory(winterAlert, new Date(0));
    assertEquals(graphicFactory.getTypes().get(0).getClass(), Snowfall.class);
  }

  @Test
  public void getType_TypeIsLakeEffectSnowWarning_ReturnsSnowfall() {
    LakeEffectSnowWarning lakeEffectSnowWarning = new LakeEffectSnowWarning();
    TypeFactory graphicFactory = new TypeFactory(lakeEffectSnowWarning, new Date(0));
    assertEquals(graphicFactory.getTypes().get(0).getClass(), Snowfall.class);
  }

  @Test
  public void getType_TypeIsWindAdvisory_ReturnsWindGusts() {
    WindAdvisory windAlert = new WindAdvisory();
    TypeFactory graphicFactory = new TypeFactory(windAlert, new Date(0));
    assertEquals(graphicFactory.getTypes().get(0).getClass(), WindGusts.class);
  }

  @Test
  public void getType_TypeIsHighWindWarning_ReturnsWindGusts() {
    HighWindWarning windAlert = new HighWindWarning();
    TypeFactory graphicFactory = new TypeFactory(windAlert, new Date(0));
    assertEquals(graphicFactory.getTypes().get(0).getClass(), WindGusts.class);
  }

  @Test
  public void getType_TypeIsFloodWatch_ReturnsWindGusts() {
    FloodWatch rainAlert = new FloodWatch();
    TypeFactory graphicFactory = new TypeFactory(rainAlert, new Date(0));
    assertEquals(graphicFactory.getTypes().get(0).getClass(), Rainfall.class);
  }

  @Test
  public void getType_TornadoWatch_ReturnsSPCOutlook() {
    TornadoWatch tornadoWatch = new TornadoWatch();
    TypeFactory graphicFactory = new TypeFactory(tornadoWatch, new Date(0));
    assertEquals(graphicFactory.getTypes().get(0).getClass(), SPCOutlook.class);
  }

  @Test
  public void getType_SevereThunderstormWatch_ReturnsSPCOutlook() {
    SevereThunderstormWatch severeThunderstormWatch = new SevereThunderstormWatch();
    TypeFactory graphicFactory = new TypeFactory(severeThunderstormWatch, new Date(0));
    assertEquals(graphicFactory.getTypes().get(0).getClass(), SPCOutlook.class);
  }

  @Test
  public void getType_HighWindWatch_ReturnsWindGusts() {
    HighWindWatch highWindWatch = new HighWindWatch();
    TypeFactory graphicFactory = new TypeFactory(highWindWatch, new Date(0));
    assertEquals(graphicFactory.getTypes().get(0).getClass(), WindGusts.class);
  }

  @Test
  public void getType_LakeWindAdvisory_ReturnsWindGusts() {
    LakeWindAdvisory lakeWindAdvisory = new LakeWindAdvisory();
    TypeFactory graphicFactory = new TypeFactory(lakeWindAdvisory, new Date(0));
    assertEquals(graphicFactory.getTypes().get(0).getClass(), WindGusts.class);
  }

  @Test
  public void getType_WinterStormWarningWithWind_ReturnsSnowAndWind() {
    WinterStormWarning winterStormWarning = new WinterStormWarning();
    winterStormWarning.setDescription("WHAT...Heavy snow expected. Total snow accumulations of 1 to 2 feet. Winds gusting as high as 45 mph.");
    TypeFactory graphicFactory = new TypeFactory(winterStormWarning, new Date(0));
    assertEquals(graphicFactory.getTypes().get(1).getClass(), WindGusts.class);
  }

  @Test
  public void getType_WinterStormWarningWithSnow_ReturnsSnow() {
    WinterStormWarning winterStormWarning = new WinterStormWarning();
    winterStormWarning.setDescription("WHAT...Heavy snow expected. Total snow accumulations of 1 to 2 feet.");
    TypeFactory graphicFactory = new TypeFactory(winterStormWarning, new Date(0));
    assertEquals(1, graphicFactory.getTypes().size());
  }

  @Test
  public void getType_FrostAdvisoryProvided_ReturnsLows() {
    FrostAdvisory frostAdvisory = new FrostAdvisory();
    TypeFactory graphicFactory = new TypeFactory(frostAdvisory, new Date(0));
    assertEquals(Lows.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_FreezeWatchProvided_ReturnsLows() {
    FreezeWatch freezeWatch = new FreezeWatch();
    TypeFactory graphicFactory = new TypeFactory(freezeWatch, new Date(0));
    assertEquals(Lows.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_FreezeWarningProvided_ReturnsLows() {
    FreezeWarning freezeWarning = new FreezeWarning();
    TypeFactory graphicFactory = new TypeFactory(freezeWarning, new Date(0));
    assertEquals(Lows.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_WinterStormWatch_ReturnsSnowfall() {
    WinterStormWatch winterStormWatch = new WinterStormWatch();
    TypeFactory graphicFactory = new TypeFactory(winterStormWatch, new Date(0));
    assertEquals(Snowfall.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_HardFreezeWatch_ReturnsLows() {
    HardFreezeWatch hardFreezeWatch = new HardFreezeWatch();
    TypeFactory graphicFactory = new TypeFactory(hardFreezeWatch, new Date(0));
    assertEquals(Lows.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_HardFreezeWarning_ReturnsLows() {
    HardFreezeWarning hardFreezeWarning = new HardFreezeWarning();
    TypeFactory graphicFactory = new TypeFactory(hardFreezeWarning, new Date(0));
    assertEquals(Lows.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_StormWarning_RadarReturned() {
    SevereThunderstormWarning svrWarn = new SevereThunderstormWarning();
    TypeFactory graphicFactory = new TypeFactory(svrWarn, new Date(0));
    assertEquals(LocalRadar.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_TornadoWarning_RadarReturn() {
    TornadoWarning torWarn = new TornadoWarning();
    TypeFactory graphicFactory = new TypeFactory(torWarn, new Date(0));
    assertEquals(LocalRadar.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_WindChillAdvisory_WindChills() {
    WindChillAdvisory windChillAdvisory = new WindChillAdvisory();
    TypeFactory graphicFactory = new TypeFactory(windChillAdvisory, new Date(0));
    assertEquals(WindChill.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_WindChillWatch_WindChills() {
    WindChillWatch windChillWatch = new WindChillWatch();
    TypeFactory graphicFactory = new TypeFactory(windChillWatch, new Date(0));
    assertEquals(WindChill.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_WindChillWarning_WindChills() {
    WindChillWarning windChillWarning = new WindChillWarning();
    TypeFactory graphicFactory = new TypeFactory(windChillWarning, new Date(0));
    assertEquals(WindChill.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_SpecialMarineWarning_Radar() {
    SpecialMarineWarning specialMarineWarning = new SpecialMarineWarning();
    TypeFactory graphicFactory = new TypeFactory(specialMarineWarning, new Date(0));
    assertEquals(LocalRadar.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_SpecialWeatherStatementWithStorms_Radar() {
    SpecialWeatherStatement specialWeatherStatement = new SpecialWeatherStatement();
    specialWeatherStatement.setDescription("At 235 AM CDT, a strong thunderstorm was located near Petit Bois\\nIsland, or 9 miles south of Moss Point, moving northeast at 35 mph.\\n\\nWinds in excess of 30 mph and half inch hail are possible with this\\nstorm.\\n\\nLocations impacted include...\\nMoss Point, Escatawpa, Gautier and Helena.\\n\\nTorrential rainfall is also occurring with this storm, and may cause\\nlocalized flooding. Do not drive your vehicle through flooded\\nroadways.\\n\\nFrequent cloud to ground lightning is occurring with this storm.\\nLightning can strike 10 miles away from a thunderstorm. Seek a safe\\nshelter inside a building or vehicle.\\n\\nThis storm may intensify, so be certain to monitor local radio\\nstations and available television stations for additional information\\nand possible warnings from the National Weather Service.");
    TypeFactory graphicFactory = new TypeFactory(specialWeatherStatement, new Date(0));
    assertEquals(LocalRadar.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_FlashFloodWatchWithThunderstormsMentioned_ExpectedRainfall() {
    FlashFloodWatch flashFloodWatch = new FlashFloodWatch();
    flashFloodWatch.setDescription("The National Weather Service in Springfield has issued a\\n\\n* Flash Flood Watch for portions of central Missouri, east\\ncentral Missouri, south central Missouri, and southwest\\nMissouri, including the following areas, in central Missouri,\\nPulaski. In east central Missouri, Phelps. In south central\\nMissouri, Dent, Howell, Oregon, Shannon, and Texas. In\\nsouthwest Missouri, Barry, Christian, Douglas, Greene,\\nLaclede, Lawrence, McDonald, Newton, Ozark, Stone, Taney,\\nWebster, and Wright.\\n\\n* From midnight CDT tonight through Thursday morning\\n\\n* Excessive rainfall amounts ranging from 1.5 to 4 inches. The\\nhighest amounts are expected to occur along and south of the\\nInterstate 44 corridor. Training thunderstorms containing high\\nrainfall rates will be possible, leading to rapid onset\\nflooding.\\n\\n* Rapid onset flooding could lead to numerous impassable roadways.\\nLow lying areas along flashy streams could also experience\\nflooding. Flashy urban flooding will also be possible.");
    TypeFactory graphicFactory = new TypeFactory(flashFloodWatch, new Date(0));
    assertEquals(Rainfall.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_FloodAdvisory_RadarRainfall() {
    FloodAdvisory floodAdvisory = new FloodAdvisory();
    TypeFactory graphicFactory = new TypeFactory(floodAdvisory, new Date(0));
    assertEquals(RadarRainfall.class, graphicFactory.getTypes().get(1).getClass());
  }

  @Test
  public void getType_FloodAdvisory_LocalRadar() {
    FloodAdvisory floodAdvisory = new FloodAdvisory();
    TypeFactory graphicFactory = new TypeFactory(floodAdvisory, new Date(0));
    assertEquals(LocalRadar.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_FloodWarning_LocalRadar() {
    FloodWarning floodWarning = new FloodWarning();
    TypeFactory graphicFactory = new TypeFactory(floodWarning, new Date(0));
    assertEquals(RadarRainfall.class, graphicFactory.getTypes().get(1).getClass());
  }

  @Test
  public void getType_FlashFloodWarning_LocalRadar() {
    FlashFloodWarning floodWarning = new FlashFloodWarning();
    TypeFactory graphicFactory = new TypeFactory(floodWarning, new Date(0));
    assertEquals(RadarRainfall.class, graphicFactory.getTypes().get(1).getClass());
  }

  @Test
  public void getType_SevereThunderstormWarningWithTorrentialRain_LocalRadar() {
    SevereThunderstormWarning severeThunderstormWarning = new SevereThunderstormWarning();
    severeThunderstormWarning.setInstruction("Torrential rainfall is occurring with this storm, and may lead to\\nflash flooding. Do not drive your vehicle through flooded roadways.");
    TypeFactory graphicFactory = new TypeFactory(severeThunderstormWarning, new Date(0));
    assertEquals(RadarRainfall.class, graphicFactory.getTypes().get(1).getClass());
  }

  @Test
  public void getType_SevereThunderstormWarning_NoRainfall() {
    SevereThunderstormWarning severeThunderstormWarning = new SevereThunderstormWarning();
    severeThunderstormWarning.setInstruction("Move to the lowest floor...");
    TypeFactory graphicFactory = new TypeFactory(severeThunderstormWarning, new Date(0));
    assertEquals(2, graphicFactory.getTypes().size());
  }

  @Test
  public void mediumTestAlert_noGraphics() {
    MediumPriorityTest mediumPriorityTest = new MediumPriorityTest();
    TypeFactory graphicFactory = new TypeFactory(mediumPriorityTest, new Date(0));
    assertEquals(0, graphicFactory.getTypes().size());
  }

  @Test
  public void highTestAlert_noGraphics() {
    HighPriorityTest highPriorityTest = new HighPriorityTest();
    TypeFactory graphicFactory = new TypeFactory(highPriorityTest, new Date(0));
    assertEquals(0, graphicFactory.getTypes().size());
  }

  @Test
  public void getType_Cancel_AlertArea() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setType(Alert.Type.CANCEL);
    TypeFactory graphicFactory = new TypeFactory(tornadoWarning, new Date(0));
    assertEquals(AlertArea.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_Cancel_OneGraphic() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setType(Alert.Type.CANCEL);
    TypeFactory graphicFactory = new TypeFactory(tornadoWarning, new Date(0));
    assertEquals(1, graphicFactory.getTypes().size());
  }

  @Test
  public void getType_PastEndTime_AlertArea() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setEndTime(new Date(0));
    TypeFactory graphicFactory = new TypeFactory(tornadoWarning, new Date(0));
    assertEquals(AlertArea.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_inActiveTestAlert_ReturnsNoGraphics() {
    HighPriorityTest testAlert = new HighPriorityTest();
    testAlert.setType(Alert.Type.CANCEL);
    TypeFactory graphicFactory = new TypeFactory(testAlert, new Date(0));
    assertEquals(0, graphicFactory.getTypes().size());
  }

  @Test
  public void getType_FlashFloodWarning_RegionalRadar() {
    FlashFloodWarning flashFloodWarning = new FlashFloodWarning();
    flashFloodWarning.setType(Alert.Type.POST);
    flashFloodWarning.setSentTime(new Date(0));
    flashFloodWarning.setStartTime(new Date(0));
    TypeFactory graphicFactory = new TypeFactory(flashFloodWarning, new Date(5));
    assertEquals(RegionalRadar.class, graphicFactory.getTypes().get(2).getClass());
  }

  @Test
  public void getType_SevereThunderstormWarning_RegionalRadar() {
    SevereThunderstormWarning severeThunderstormWarning = new SevereThunderstormWarning();
    severeThunderstormWarning.setType(Alert.Type.POST);
    severeThunderstormWarning.setSentTime(new Date(0));
    severeThunderstormWarning.setStartTime(new Date(0));
    TypeFactory graphicFactory = new TypeFactory(severeThunderstormWarning, new Date(5));
    assertEquals(RegionalRadar.class, graphicFactory.getTypes().get(1).getClass());
  }

  @Test
  public void getType_FlashFloodWatch_RegionalRadar() {
    FlashFloodWatch severeThunderstormWarning = new FlashFloodWatch();
    severeThunderstormWarning.setType(Alert.Type.POST);
    severeThunderstormWarning.setSentTime(new Date(0));
    severeThunderstormWarning.setStartTime(new Date(3));
    TypeFactory graphicFactory = new TypeFactory(severeThunderstormWarning, new Date(5));
    assertEquals(RegionalRadar.class, graphicFactory.getTypes().get(1).getClass());
  }

  @Test
  public void getType_FlashFloodWatchStartingIn6Hours_RegionalRadar() {
    FlashFloodWatch severeThunderstormWarning = new FlashFloodWatch();
    severeThunderstormWarning.setType(Alert.Type.POST);
    severeThunderstormWarning.setSentTime(new Date(0));
    severeThunderstormWarning.setStartTime(new Date(6 * 60 * 60 * 1000));
    TypeFactory graphicFactory = new TypeFactory(severeThunderstormWarning, new Date(0));
    assertEquals(1, graphicFactory.getTypes().size());
  }

  @Test
  public void getType_WinterStormWarning_RegionalRadar() {
    WinterStormWarning winterStormWarning = new WinterStormWarning();
    winterStormWarning.setType(Alert.Type.POST);
    winterStormWarning.setSentTime(new Date(0));
    winterStormWarning.setStartTime(new Date(3));
    TypeFactory graphicFactory = new TypeFactory(winterStormWarning, new Date(5));
    assertEquals(RegionalRadar.class, graphicFactory.getTypes().get(0).getClass());
  }


  @Test
  public void getType_ThunderstormWarning_OneHourPrecipitation() {
    SevereThunderstormWarning severeThunderstormWarning = new SevereThunderstormWarning();
    severeThunderstormWarning.setMotionVector(new MotionVector(0, 0));
    TypeFactory graphicFactory = new TypeFactory(severeThunderstormWarning, new Date(5));
    assertEquals(OneHourPrecipitation.class, graphicFactory.getTypes().get(1).getClass());
  }

  @Test
  public void getType_ThunderstormWarningWithNoStormMotion_NoOneHourPrecip() {
    SevereThunderstormWarning severeThunderstormWarning = new SevereThunderstormWarning();
    TypeFactory graphicFactory = new TypeFactory(severeThunderstormWarning, new Date(5));
    assertEquals(RegionalRadar.class, graphicFactory.getTypes().get(1).getClass());
  }

  @Test
  public void getType_ExcessiveHeatWatch_HeatIndex() {
    ExcessiveHeatWatch excessiveHeatWatch = new ExcessiveHeatWatch();
    TypeFactory graphicFactory = new TypeFactory(excessiveHeatWatch, new Date(5));
    assertEquals(HeatIndex.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_ExcessiveHeatWarning_HeatIndex() {
    ExcessiveHeatWarning excessiveHeatWarning = new ExcessiveHeatWarning();
    TypeFactory graphicFactory = new TypeFactory(excessiveHeatWarning, new Date(5));
    assertEquals(HeatIndex.class, graphicFactory.getTypes().get(0).getClass());
  }

  @Test
  public void getType_HeatAdvisory_HeatIndex() {
    HeatAdvisory excessiveHeatWarning = new HeatAdvisory();
    TypeFactory graphicFactory = new TypeFactory(excessiveHeatWarning, new Date(5));
    assertEquals(HeatIndex.class, graphicFactory.getTypes().get(0).getClass());
  }
}
