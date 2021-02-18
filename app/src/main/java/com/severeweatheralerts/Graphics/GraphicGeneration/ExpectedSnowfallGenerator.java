package com.severeweatheralerts.Graphics.GraphicGeneration;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.URLGeneration.ExpectedSnowfallURLGenerator;
import com.severeweatheralerts.Graphics.URLGeneration.URLGenerator;
import com.severeweatheralerts.Location.Location;

public class ExpectedSnowfallGenerator extends GraphicGenerator {
  public ExpectedSnowfallGenerator(Context context, Alert alert, Location location) {
    super(context, alert, location);
  }

  @Override
  protected URLGenerator getURLGenerator() {
    return new ExpectedSnowfallURLGenerator(context, bound, location);
  }
}
