package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.Location.Location;

public class AlertAreaGenerator extends GraphicGenerator {
  public AlertAreaGenerator(Context context, Alert alert, Location location) {
    super(context, alert, location);
  }

  @Override
  protected void getURLs() {
    layers.add(new Layer(new URL().getCountyMap(bound)));
    layers.add(new Layer(getZoneOverlay()));
  }
}
