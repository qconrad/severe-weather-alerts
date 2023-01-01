package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Graphics.Generators.IceAccumulationGenerator;

public class IceAccumulation implements GraphicType {
  public IceAccumulation() {}

  @Override
  public String getTitle() {
    return "Expected Ice Accumulation";
  }

  @Override
  public int getValidDuration() {
    return 30 * 60 * 1000;
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, GCSCoordinate location) {
    return new IceAccumulationGenerator(context, alert, location);
  }
}
