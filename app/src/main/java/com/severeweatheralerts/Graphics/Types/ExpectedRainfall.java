package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.GraphicGeneration.ExpectedRainfallGenerator;
import com.severeweatheralerts.Graphics.GraphicGeneration.GraphicGenerator;
import com.severeweatheralerts.Location.Location;

public class ExpectedRainfall extends AlertArea implements GraphicType {
  public ExpectedRainfall() {}

  @Override
  public String getTitle() {
    return "Expected Rainfall";
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, Location location) {
    return new ExpectedRainfallGenerator(context, alert, location);
  }
}
