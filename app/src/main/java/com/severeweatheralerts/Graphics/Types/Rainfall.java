package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.RainfallGenerator;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Location.Location;

public class Rainfall extends AlertArea implements GraphicType {
  public Rainfall() {}

  @Override
  public String getTitle() {
    return "Expected Rainfall";
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, GCSCoordinate location) {
    return new RainfallGenerator(context, alert, location);
  }
}
