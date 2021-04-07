package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Graphics.Generators.RadarGenerator;
import com.severeweatheralerts.Location.Location;

public class Radar extends AlertArea implements GraphicType {
  public Radar() {}

  @Override
  public String getTitle() {
    return "Radar";
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, Location location) {
    return new RadarGenerator(context, alert, location);
  }
}
