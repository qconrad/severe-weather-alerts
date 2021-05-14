package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Graphics.Generators.SPCOutlookGenerator;
import com.severeweatheralerts.Graphics.Generators.SnowfallGenerator;
import com.severeweatheralerts.Location.Location;

public class SPCOutlook implements GraphicType {
  public SPCOutlook() {}

  @Override
  public String getTitle() {
    return "Risk Area";
  }

  @Override
  public int getValidDuration() {
    return 30 * 60 * 1000;
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, GCSCoordinate location) {
    return new SPCOutlookGenerator(context, alert, location);
  }
}
