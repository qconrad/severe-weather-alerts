package com.severeweatheralerts.Graphics.GraphicGeneration;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.TextUtils.Plurality;

public class ExpectedRainfallGenerator extends GraphicGenerator {
  public ExpectedRainfallGenerator(Context context, Alert alert, Location location) {
    super(context, alert, location);
    needsMapTimes = true;
    gridParameter = "quantitativePrecipitation";
  }

  @Override
  protected void getURLs() {
    String dateString = mapTimes.get(mapTimes.size()-1).getString();
    urls.add(new URL().getTotalRain(bound, "conus", dateString));
    urls.add(new URL().getTotalRainPoints(bound, "conus", dateString));
    urls.add(new URL().getCountyMap(bound));
  }

  @Override
  protected String getSubText() {
    double rainfallAmt = getRainfallInches();
    return rainfallAmt + new Plurality(rainfallAmt, " inch", " inches").getText();
  }

  private double getRainfallInches() {
    return new SumCalculator(gridData).getSum() / 25.4;
  }
}
