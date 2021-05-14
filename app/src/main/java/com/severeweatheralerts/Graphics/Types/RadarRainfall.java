package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Graphics.Generators.RadarRainfallGenerator;

public class RadarRainfall implements GraphicType {
  public RadarRainfall() {}

  @Override
  public String getTitle() {
    return "Estimated Rainfall";
  }

  @Override
  public int getValidDuration() {
    return 5 * 60 * 1000;
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, GCSCoordinate location) {
    return new RadarRainfallGenerator(context, alert, location);
  }
}
