package com.severeweatheralerts.Graphics.GraphicGeneration;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Location.Location;

public class ExpectedSnowfallGenerator extends GraphicGenerator {
  public ExpectedSnowfallGenerator(Context context, Alert alert, Location location) {
    super(context, alert, location);
    needsMapTimes = true;
    gridParameter = "snowfallAmount";
  }

  @Override
  protected void getURLs() {
    String dateString = mapTimes.get(mapTimes.size()-1).getString();
    urls.add(new URL().getTotalSnow(bound, "conus", dateString));
    urls.add(new URL().getTotalSnowPoints(bound, "conus", dateString));
    urls.add(new URL().getCountyMap(bound));
  }

  @Override
  protected String getSubText() {
    return new SumCalculator(gridData).getSum() + "mm of snow expected";
  }
}
