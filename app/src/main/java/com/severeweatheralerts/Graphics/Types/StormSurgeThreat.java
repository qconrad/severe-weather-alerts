package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Graphics.Generators.StormSurgeThreatGenerator;

public class StormSurgeThreat implements GraphicType {
  public StormSurgeThreat() {}

  @Override
  public String getTitle() {
    return "Storm Surge Threat";
  }

  @Override
  public int getValidDuration() {
    return 30 * 60 * 1000;
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, GCSCoordinate location) {
    return new StormSurgeThreatGenerator(context, alert, location);
  }
}
