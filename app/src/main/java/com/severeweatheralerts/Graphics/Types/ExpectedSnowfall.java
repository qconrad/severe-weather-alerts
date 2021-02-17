package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.GraphicGeneration.ExpectedSnowfallGenerator;
import com.severeweatheralerts.Graphics.GraphicGeneration.GraphicGenerator;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;

public class ExpectedSnowfall extends AlertArea implements GraphicType {
  public ExpectedSnowfall() {}

  @Override
  public String getTitle() {
    return "Expected Snowfall";
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, MercatorCoordinate location) {
    return new ExpectedSnowfallGenerator(context, alert, location);
  }
}
