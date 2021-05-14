package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.AlertAreaGenerator;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Location.Location;

public class AlertArea implements GraphicType {
  public AlertArea() { }

  public String getTitle() {
    return "Alert Area";
  }

  @Override
  public int getValidDuration() {
    return 0;
  }

  @Override
  public GraphicGenerator getGenerator(Context context, Alert alert, GCSCoordinate location) {
    return new AlertAreaGenerator(context, alert, location);
  }
}
