package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Graphics.Generators.WindGustsGenerator;
import com.severeweatheralerts.Location.Location;

public class WindGusts implements GraphicType {
  @Override
  public String getTitle() {
    return "Expected Wind Gusts";
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, Location location) {
    return new WindGustsGenerator(context, alert, location);
  }
}