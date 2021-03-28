package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.GridData.ParameterTrim;
import com.severeweatheralerts.Graphics.NextMapTimeFromDate;
import com.severeweatheralerts.Graphics.Rounder;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.TextUtils.Plurality;

import static com.severeweatheralerts.Constants.SNOWFALL_AMOUNT_DECIMAL_PLACES;

public class SnowfallGenerator extends GraphicGenerator {
  public SnowfallGenerator(Context context, Alert alert, Location location) {
    super(context, alert, location);
    mapTimeParameter = "snowamt";
    gridParameter = "snowfallAmount";
  }

  @Override
  protected void getURLs() {
    String dateString = new NextMapTimeFromDate(mapTimes, alert.getEndTime()).getMapTime().getString();
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
    ParameterTrim parameterTrim = new ParameterTrim(gridData);
    parameterTrim.trimRight(alert.getEndTime());
    return new SumCalculator(parameterTrim.getTrimmed()).getSum() / 25.4;
  }
}
