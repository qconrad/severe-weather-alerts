package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.NWS.WinterWeatherAdvisory;
import com.severeweatheralerts.Graphics.AlertArea;
import com.severeweatheralerts.Graphics.ExpectedSnowfall;
import com.severeweatheralerts.Graphics.GraphicType;

import java.util.ArrayList;

public class GraphicFactory {
  private final Alert alert;

  public GraphicFactory(Alert alert) {
    this.alert = alert;
  }

  public ArrayList<GraphicType> getTypes() {
    ArrayList<GraphicType> types = new ArrayList<>();
    if (alert instanceof WinterWeatherAdvisory)
      types.add(new ExpectedSnowfall());
    else
      types.add(new AlertArea());
    return types;
  }
}
