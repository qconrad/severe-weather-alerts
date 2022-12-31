package com.severeweatheralerts.Graphics.Types;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.NWS.ExcessiveHeatWarning;
import com.severeweatheralerts.Alerts.NWS.ExcessiveHeatWatch;
import com.severeweatheralerts.Alerts.NWS.ExtremeWindWarning;
import com.severeweatheralerts.Alerts.NWS.FlashFloodWarning;
import com.severeweatheralerts.Alerts.NWS.FlashFloodWatch;
import com.severeweatheralerts.Alerts.NWS.FloodAdvisory;
import com.severeweatheralerts.Alerts.NWS.FloodWarning;
import com.severeweatheralerts.Alerts.NWS.FloodWatch;
import com.severeweatheralerts.Alerts.NWS.FreezeWarning;
import com.severeweatheralerts.Alerts.NWS.FreezeWatch;
import com.severeweatheralerts.Alerts.NWS.FrostAdvisory;
import com.severeweatheralerts.Alerts.NWS.HardFreezeWarning;
import com.severeweatheralerts.Alerts.NWS.HardFreezeWatch;
import com.severeweatheralerts.Alerts.NWS.HeatAdvisory;
import com.severeweatheralerts.Alerts.NWS.HurricaneLocalStatement;
import com.severeweatheralerts.Alerts.NWS.HurricaneWarning;
import com.severeweatheralerts.Alerts.NWS.HurricaneWatch;
import com.severeweatheralerts.Alerts.NWS.LakeEffectSnowWarning;
import com.severeweatheralerts.Alerts.NWS.SevereThunderstormWarning;
import com.severeweatheralerts.Alerts.NWS.SevereThunderstormWatch;
import com.severeweatheralerts.Alerts.NWS.SpecialMarineWarning;
import com.severeweatheralerts.Alerts.NWS.TornadoWarning;
import com.severeweatheralerts.Alerts.NWS.TornadoWatch;
import com.severeweatheralerts.Alerts.NWS.TropicalStormWarning;
import com.severeweatheralerts.Alerts.NWS.TropicalStormWatch;
import com.severeweatheralerts.Alerts.NWS.WindChillAdvisory;
import com.severeweatheralerts.Alerts.NWS.WindChillWarning;
import com.severeweatheralerts.Alerts.NWS.WindChillWatch;
import com.severeweatheralerts.Alerts.NWS.WinterStormWarning;
import com.severeweatheralerts.Alerts.NWS.WinterStormWatch;
import com.severeweatheralerts.Alerts.NWS.WinterWeatherAdvisory;
import com.severeweatheralerts.Alerts.TestAlerts.TestAlert;
import com.severeweatheralerts.Constants;

import java.util.ArrayList;
import java.util.Date;

public class TypeFactory {
  private final ArrayList<GraphicType> types = new ArrayList<>();
  private final Alert alert;
  private final Date date;

  public TypeFactory(Alert alert, Date date) {
    this.alert = alert;
    this.date = date;
  }

  public ArrayList<GraphicType> getTypes() {
    if (alert instanceof TestAlert) return types;
    else if (!alert.activeAt(date)) {
      types.add(new AlertArea());
      return types;
    }
    else if (alert instanceof WinterWeatherAdvisory || alert instanceof WinterStormWarning || alert instanceof LakeEffectSnowWarning || alert instanceof WinterStormWatch) {
      if (alert.startsBefore(new Date(date.getTime() + Constants.COMPOSITE_RADAR_SHOW_BEFORE_EVENT_TIME)))
        types.add(new RegionalRadar(400));
      if ((descriptionContains("feet") || descriptionContains("inches")) && alert.endsBefore(new Date(date.getTime() + Constants.SNOWFALL_AMOUNT_TIME_RANGE)))
        types.add(new Snowfall());
    }
    else if (alert instanceof WindChillAdvisory || alert instanceof WindChillWatch || alert instanceof WindChillWarning)
      types.add(new WindChill());
    else if (alert instanceof ExcessiveHeatWatch || alert instanceof ExcessiveHeatWarning || alert instanceof HeatAdvisory)
      types.add(new HeatIndex());
    else if (alert instanceof FrostAdvisory || alert instanceof FreezeWatch || alert instanceof FreezeWarning || alert instanceof HardFreezeWatch || alert instanceof HardFreezeWarning)
      types.add(new Lows());
    else if (alert instanceof FlashFloodWatch || alert instanceof FloodWatch) {
      if (descriptionContains(" inch") && alert.endsBefore(new Date(date.getTime() + Constants.RAINFALL_AMOUNT_TIME_RANGE)))
        types.add(new Rainfall());
      if (alert.startsBefore(new Date(date.getTime() + Constants.COMPOSITE_RADAR_SHOW_BEFORE_EVENT_TIME))) {
        types.add(new RegionalRadar(300));
      }
    }
    else if (alert instanceof FloodAdvisory || alert instanceof FloodWarning || alert instanceof FlashFloodWarning) {
      types.add(new LocalRadar());
      types.add(new RadarRainfall());
      types.add(new RegionalRadar(400));
    }
    else if (alert instanceof ExtremeWindWarning)
      types.add(new LocalRadar());
    else if (alert instanceof TornadoWatch || alert instanceof SevereThunderstormWatch) {
      types.add(new SPCOutlook());
    }
    else if (alert instanceof TropicalStormWatch || alert instanceof TropicalStormWarning) {
      types.add(new AlertArea());
      types.add(new ProbabilityTropicalWinds());
      types.add(new RegionalRadar(400));
    }
    else if (alert instanceof HurricaneWarning || alert instanceof HurricaneWatch) {
      types.add(new AlertArea());
      types.add(new ProbabilityTropicalWinds());
      types.add(new ProbabilityHurricaneWinds());
      types.add(new RegionalRadar(400));
    }
    else if (alert instanceof HurricaneLocalStatement) {
      types.add(new HurricaneWindThreat());
      types.add(new HurricaneFloodingThreat());
      types.add(new HurricaneTornadoThreat());
      types.add(new StormSurgeThreat());
    }
    else if (thunderstorms()) {
      types.add(new LocalRadar());
      if (alert.hasMotionVector()) types.add(new OneHourPrecipitation());
      if (torrentialRainfall()) types.add(new RadarRainfall());
      types.add(new RegionalRadar(250));
    }
    if (types.size() == 0) types.add(new AlertArea());
    return types;
  }

  private boolean torrentialRainfall() {
    return instructionContains("Torrential rainfall") || descriptionContains("Torrential rainfall");
  }

  private boolean thunderstorms() {
    return alert instanceof SevereThunderstormWarning || alert instanceof TornadoWarning || alert instanceof SpecialMarineWarning || descriptionContains("thunderstorm");
  }

  private boolean descriptionContains(String text) {
    return alert.getDescription() != null && alert.getDescription().contains(text);
  }

  private boolean instructionContains(String text) {
    return alert.getInstruction() != null && alert.getInstruction().contains(text);
  }
}
