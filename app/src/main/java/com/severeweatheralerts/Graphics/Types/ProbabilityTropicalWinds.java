package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Graphics.Generators.ProbabilityTropicalWindsGenerator;

public class ProbabilityTropicalWinds implements GraphicType {
  public ProbabilityTropicalWinds() {}

  @Override
  public String getTitle() {
    return "Tropical Storm Force Winds";
  }

  @Override
  public int getValidDuration() {
    return 30 * 60 * 1000;
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, GCSCoordinate location) {
    return new ProbabilityTropicalWindsGenerator(context, alert, location);
  }
}
