package com.severeweatheralerts.Graphics.Types;

import com.severeweatheralerts.Alerts.Alert;
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
import com.severeweatheralerts.Alerts.NWS.HighWindWarning;
import com.severeweatheralerts.Alerts.NWS.HighWindWatch;
import com.severeweatheralerts.Alerts.NWS.LakeEffectSnowWarning;
import com.severeweatheralerts.Alerts.NWS.LakeWindAdvisory;
import com.severeweatheralerts.Alerts.NWS.SevereThunderstormWarning;
import com.severeweatheralerts.Alerts.NWS.SevereThunderstormWatch;
import com.severeweatheralerts.Alerts.NWS.SpecialMarineWarning;
import com.severeweatheralerts.Alerts.NWS.TornadoWarning;
import com.severeweatheralerts.Alerts.NWS.TornadoWatch;
import com.severeweatheralerts.Alerts.NWS.WindAdvisory;
import com.severeweatheralerts.Alerts.NWS.WindChillAdvisory;
import com.severeweatheralerts.Alerts.NWS.WindChillWarning;
import com.severeweatheralerts.Alerts.NWS.WindChillWatch;
import com.severeweatheralerts.Alerts.NWS.WinterStormWarning;
import com.severeweatheralerts.Alerts.NWS.WinterStormWatch;
import com.severeweatheralerts.Alerts.NWS.WinterWeatherAdvisory;
import com.severeweatheralerts.Alerts.TestAlerts.TestAlert;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Graphics.Generators.OneHourPrecipitationGenerator;

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
      if (!alert.startsBefore(new Date(date.getTime() + Constants.COMPOSITE_RADAR_SHOW_BEFORE_EVENT_TIME)))
        types.add(new RegionalRadar(400));
      types.add(new Snowfall());
      if (alert.getDescription() != null && alert.getDescription().contains("Wind"))
        types.add(new WindGusts());
    }
    else if (alert instanceof WindChillAdvisory || alert instanceof WindChillWatch || alert instanceof WindChillWarning)
      types.add(new WindChill());
    else if (alert instanceof FrostAdvisory || alert instanceof FreezeWatch || alert instanceof FreezeWarning || alert instanceof HardFreezeWatch || alert instanceof HardFreezeWarning)
      types.add(new Lows());
    else if (alert instanceof FlashFloodWatch || alert instanceof FloodWatch) {
      types.add(new Rainfall());
      if (!alert.startsBefore(new Date(date.getTime() + Constants.COMPOSITE_RADAR_SHOW_BEFORE_EVENT_TIME))) {
        types.add(new RegionalRadar(300));
      }
    }
    else if (alert instanceof FloodAdvisory || alert instanceof FloodWarning || alert instanceof FlashFloodWarning) {
      types.add(new LocalRadar());
      types.add(new RadarRainfall());
      types.add(new RegionalRadar(400));
    }
    else if (alert instanceof WindAdvisory || alert instanceof HighWindWarning || alert instanceof HighWindWatch || alert instanceof LakeWindAdvisory)
      types.add(new WindGusts());
    else if (alert instanceof TornadoWatch || alert instanceof SevereThunderstormWatch) {
      types.add(new SPCOutlook());
    }
    else if (thunderstorms()) {
      types.add(new LocalRadar());
      types.add(new OneHourPrecipitation());
      if (alert.getInstruction() != null && alert.getInstruction().contains("Torrential rainfall")) types.add(new RadarRainfall());
      types.add(new RegionalRadar(250));
    }
    else
      types.add(new AlertArea());
    return types;
  }

  private boolean thunderstorms() {
    return alert instanceof SevereThunderstormWarning || alert instanceof TornadoWarning || alert instanceof SpecialMarineWarning || alert.getDescription() != null && alert.getDescription().contains("thunderstorm");
  }
}
