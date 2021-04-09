package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.SnowfallGenerator;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Location.Location;

public class Snowfall extends AlertArea implements GraphicType {
  public Snowfall() {}

  @Override
  public String getTitle() {
    return "Expected Snowfall";
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, GCSCoordinate location) {
    return new SnowfallGenerator(context, alert, location);
  }
}
