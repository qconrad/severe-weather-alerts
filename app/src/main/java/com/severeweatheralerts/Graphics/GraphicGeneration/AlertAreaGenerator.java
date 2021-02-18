package com.severeweatheralerts.Graphics.GraphicGeneration;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.URLGeneration.AlertAreaURLGenerator;
import com.severeweatheralerts.Graphics.URLGeneration.URLGenerator;
import com.severeweatheralerts.Location.Location;

public class AlertAreaGenerator extends GraphicGenerator {
  public AlertAreaGenerator(Context context, Alert alert, Location location) {
    super(context, alert, location);
  }

  @Override
  protected URLGenerator getURLGenerator() {
    return new AlertAreaURLGenerator(bound);
  }
}