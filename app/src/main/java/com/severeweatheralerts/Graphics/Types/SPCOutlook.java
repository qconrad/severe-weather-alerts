package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Graphics.Generators.SPCOutlookGenerator;
import com.severeweatheralerts.Graphics.Generators.SnowfallGenerator;
import com.severeweatheralerts.Location.Location;

public class SPCOutlook extends AlertArea implements GraphicType {
  public SPCOutlook() {}

  @Override
  public String getTitle() {
    return "Risk Area";
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, Location location) {
    return new SPCOutlookGenerator(context, alert, location);
  }
}
