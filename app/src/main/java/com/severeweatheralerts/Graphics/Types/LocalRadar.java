package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Graphics.Generators.LocalRadarGenerator;

public class LocalRadar implements GraphicType {
  public LocalRadar() {}

  @Override
  public String getTitle() {
    return "Radar";
  }

  @Override
  public int getValidDuration() {
    return 60 * 1000;
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, GCSCoordinate location) {
    return new LocalRadarGenerator(context, alert, location);
  }
}
