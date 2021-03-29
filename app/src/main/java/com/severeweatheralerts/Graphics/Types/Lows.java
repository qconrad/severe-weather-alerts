package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Graphics.Generators.LowsGenerator;
import com.severeweatheralerts.Location.Location;

public class Lows extends AlertArea implements GraphicType {
  public Lows() {}

  @Override
  public String getTitle() {
    return "Expected Lows";
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, Location location) {
    return new LowsGenerator(context, alert, location);
  }
}
