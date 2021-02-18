package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.GraphicGeneration.AlertAreaGenerator;
import com.severeweatheralerts.Graphics.GraphicGeneration.GraphicGenerator;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Location.Location;

public class AlertArea implements GraphicType {
  public AlertArea() { }

  public String getTitle() {
    return "Alert Area";
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, Location location) {
    return new AlertAreaGenerator(context, alert, location);
  }
}
