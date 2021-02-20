package com.severeweatheralerts.Graphics.GraphicGeneration;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.NWS.FlashFloodWatch;
import com.severeweatheralerts.Alerts.NWS.LakeEffectSnowWarning;
import com.severeweatheralerts.Alerts.NWS.WinterStormWarning;
import com.severeweatheralerts.Alerts.NWS.WinterWeatherAdvisory;
import com.severeweatheralerts.Graphics.Types.AlertArea;
import com.severeweatheralerts.Graphics.Types.ExpectedRainfall;
import com.severeweatheralerts.Graphics.Types.ExpectedSnowfall;
import com.severeweatheralerts.Graphics.Types.GraphicType;

import java.util.ArrayList;

public class GraphicFactory {
  private final Alert alert;

  public GraphicFactory(Alert alert) {
    this.alert = alert;
  }

  public ArrayList<GraphicType> getTypes() {
    ArrayList<GraphicType> types = new ArrayList<>();
    if (alert instanceof WinterWeatherAdvisory || alert instanceof WinterStormWarning || alert instanceof LakeEffectSnowWarning)
      types.add(new ExpectedSnowfall());
    else if (alert instanceof FlashFloodWatch)
      types.add(new ExpectedRainfall());
    else
      types.add(new AlertArea());
    return types;
  }
}
