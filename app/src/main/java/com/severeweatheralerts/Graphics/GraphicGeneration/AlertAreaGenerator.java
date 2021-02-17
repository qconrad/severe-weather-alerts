package com.severeweatheralerts.Graphics.GraphicGeneration;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.URLGeneration.AlertAreaURLGenerator;
import com.severeweatheralerts.Graphics.URLGeneration.URLGenerator;

public class AlertAreaGenerator extends GraphicGenerator {
  public AlertAreaGenerator(Context context, Alert alert, MercatorCoordinate location) {
    super(context, alert, location);
  }

  @Override
  protected URLGenerator getURLGenerator() {
    return new AlertAreaURLGenerator(bound);
  }
}
