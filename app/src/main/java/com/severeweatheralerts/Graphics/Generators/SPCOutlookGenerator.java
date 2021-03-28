package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Location.Location;

public class SPCOutlookGenerator extends GraphicGenerator {
  public SPCOutlookGenerator(Context context, Alert alert, Location location) {
    super(context, alert, location);
    mapTimeParameter = "convoutlook";
  }

  @Override
  protected void getURLs() {
    String dateString = mapTimes.get(0).getString();
    urls.add(new URL().getSpcOutlook(bound, "conus", dateString));
    urls.add(new URL().getCountyMap(bound));
    urls.add(new URL().getSpcOutlookPoints(bound, "conus", dateString));
  }
}
