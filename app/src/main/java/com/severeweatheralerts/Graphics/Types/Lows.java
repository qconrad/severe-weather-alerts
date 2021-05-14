package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Graphics.Generators.LowsGenerator;
import com.severeweatheralerts.Location.Location;

public class Lows implements GraphicType {
  public Lows() {}

  @Override
  public String getTitle() {
    return "Expected Lows";
  }

  @Override
  public int getValidDuration() {
    return 30 * 60 * 1000;
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, GCSCoordinate location) {
    return new LowsGenerator(context, alert, location);
  }
}
