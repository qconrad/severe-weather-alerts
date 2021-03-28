package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.Location.Location;

public class SPCOutlookGenerator extends GraphicGenerator {
  public SPCOutlookGenerator(Context context, Alert alert, Location location) {
    super(context, alert, location);
    mapTimeParameter = "convoutlook";
  }

  @Override
  protected void getURLs() {
    String dateString = mapTimes.get(0).getString();
    layers.add(new Layer(new URL().getSpcOutlook(bound, "conus", dateString)));
    layers.add(new Layer(new URL().getCountyMap(bound)));
    layers.add(new Layer(getZoneOverlay()));
    layers.add(new Layer(new URL().getSpcOutlookPoints(bound, "conus", dateString)));
  }
}
