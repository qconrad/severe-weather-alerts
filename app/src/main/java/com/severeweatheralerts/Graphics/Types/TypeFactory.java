package com.severeweatheralerts.Graphics.Types;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.NWS.FlashFloodWatch;
import com.severeweatheralerts.Alerts.NWS.FloodWatch;
import com.severeweatheralerts.Alerts.NWS.HighWindWarning;
import com.severeweatheralerts.Alerts.NWS.LakeEffectSnowWarning;
import com.severeweatheralerts.Alerts.NWS.SevereThunderstormWatch;
import com.severeweatheralerts.Alerts.NWS.TornadoWatch;
import com.severeweatheralerts.Alerts.NWS.WindAdvisory;
import com.severeweatheralerts.Alerts.NWS.WinterStormWarning;
import com.severeweatheralerts.Alerts.NWS.WinterWeatherAdvisory;

import java.util.ArrayList;

public class TypeFactory {
  private final ArrayList<GraphicType> types = new ArrayList<>();
  private final Alert alert;

  public TypeFactory(Alert alert) {
    this.alert = alert;
  }

  public ArrayList<GraphicType> getTypes() {
    if (alert instanceof WinterWeatherAdvisory || alert instanceof WinterStormWarning || alert instanceof LakeEffectSnowWarning)
      types.add(new Snowfall());
    else if (alert instanceof FlashFloodWatch || alert instanceof FloodWatch)
      types.add(new Rainfall());
    else if (alert instanceof WindAdvisory || alert instanceof HighWindWarning)
      types.add(new WindGusts());
    else if (alert instanceof TornadoWatch || alert instanceof SevereThunderstormWatch)
      types.add(new SPCOutlook());
    else
      types.add(new AlertArea());
    return types;
  }
}
