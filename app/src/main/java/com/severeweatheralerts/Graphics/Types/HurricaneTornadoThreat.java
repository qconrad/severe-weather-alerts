package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Graphics.Generators.HurricaneTornadoThreatGenerator;

public class HurricaneTornadoThreat implements GraphicType {
  public HurricaneTornadoThreat() {}

  @Override
  public String getTitle() {
    return "Tornado Threat";
  }

  @Override
  public int getValidDuration() {
    return 30 * 60 * 1000;
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, GCSCoordinate location) {
    return new HurricaneTornadoThreatGenerator(context, alert, location);
  }
}
