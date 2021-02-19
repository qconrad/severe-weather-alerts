package com.severeweatheralerts.Graphics.GraphicGeneration;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Rounder;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.TextUtils.Plurality;

import static com.severeweatheralerts.Constants.SNOWFALL_AMOUNT_DECIMAL_PLACES;

public class ExpectedSnowfallGenerator extends GraphicGenerator {
  public ExpectedSnowfallGenerator(Context context, Alert alert, Location location) {
    super(context, alert, location);
    mapTimeParameter = "snowamt";
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
    double snowfallAmt = new Rounder(getSnowfallInches(), SNOWFALL_AMOUNT_DECIMAL_PLACES).getRounded();
    return snowfallAmt + new Plurality(snowfallAmt, " inch", " inches").getText();
  }

  private double getSnowfallInches() {
    return new SumCalculator(gridData).getSum() / 25.4;
  }
}
