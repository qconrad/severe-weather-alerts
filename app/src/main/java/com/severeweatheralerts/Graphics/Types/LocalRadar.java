package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Graphics.Generators.LocalRadarGenerator;

public class LocalRadar extends AlertArea implements GraphicType {
  public LocalRadar() {}

  @Override
  public String getTitle() {
    return "Radar";
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, GCSCoordinate location) {
    return new LocalRadarGenerator(context, alert, location);
  }
}
