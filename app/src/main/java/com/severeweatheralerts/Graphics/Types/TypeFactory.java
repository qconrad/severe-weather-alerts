package com.severeweatheralerts.Graphics.Types;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.NWS.FlashFloodWatch;
import com.severeweatheralerts.Alerts.NWS.HighWindWarning;
import com.severeweatheralerts.Alerts.NWS.LakeEffectSnowWarning;
import com.severeweatheralerts.Alerts.NWS.WindAdvisory;
import com.severeweatheralerts.Alerts.NWS.WinterStormWarning;
import com.severeweatheralerts.Alerts.NWS.WinterWeatherAdvisory;
import com.severeweatheralerts.Graphics.Types.AlertArea;
import com.severeweatheralerts.Graphics.Types.Rainfall;
import com.severeweatheralerts.Graphics.Types.Snowfall;
import com.severeweatheralerts.Graphics.Types.GraphicType;
import com.severeweatheralerts.Graphics.Types.WindGusts;

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
    else if (alert instanceof FlashFloodWatch)
      types.add(new Rainfall());
    else if (alert instanceof WindAdvisory || alert instanceof HighWindWarning)
      types.add(new WindGusts());
    else
      types.add(new AlertArea());
    return types;
  }
}
