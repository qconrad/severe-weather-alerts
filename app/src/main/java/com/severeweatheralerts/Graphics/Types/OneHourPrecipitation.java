package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Graphics.Generators.OneHourPrecipitation.OneHourPrecipitationGenerator;

public class OneHourPrecipitation implements GraphicType {
  @Override
  public String getTitle() {
    return "One Hour Forecast";
  }

  @Override
  public int getValidDuration() {
    return 10 * 1000;
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, GCSCoordinate location) {
    return new OneHourPrecipitationGenerator(context, alert, location);
  }
}
