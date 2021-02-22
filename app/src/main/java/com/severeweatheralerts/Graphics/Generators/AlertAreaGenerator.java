package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Location.Location;

public class AlertAreaGenerator extends GraphicGenerator {
  public AlertAreaGenerator(Context context, Alert alert, Location location) {
    super(context, alert, location);
  }

  @Override
  protected void getURLs() {
    urls.add(new URL().getCountyMap(bound));
  }
}
