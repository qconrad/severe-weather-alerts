package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Graphics.Generators.HurricaneWindThreatGenerator;

public class HurricaneWindThreat implements GraphicType {
  public HurricaneWindThreat() {}

  @Override
  public String getTitle() {
    return "Wind Threat";
  }

  @Override
  public int getValidDuration() {
    return 30 * 60 * 1000;
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, GCSCoordinate location) {
    return new HurricaneWindThreatGenerator(context, alert, location);
  }
}
