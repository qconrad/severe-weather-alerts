package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Graphics.Generators.RegionalRadarGenerator;

public class RegionalRadar implements GraphicType {
  private final int ZOOM_SIZE;

  public RegionalRadar(int ZOOM_SIZE) {
    this.ZOOM_SIZE = ZOOM_SIZE;
  }

  @Override
  public String getTitle() {
    return "Regional Radar";
  }

  @Override
  public int getValidDuration() {
    return 5 * 60 * 1000;
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, GCSCoordinate location) {
    return new RegionalRadarGenerator(context, alert, location, ZOOM_SIZE);
  }
}
