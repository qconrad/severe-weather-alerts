package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.CompositeRadarGenerator;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;

public class CompositeRadar extends LocalRadar {
  public CompositeRadar() {}

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, GCSCoordinate location) {
    return new CompositeRadarGenerator(context, alert, location);
  }
}
