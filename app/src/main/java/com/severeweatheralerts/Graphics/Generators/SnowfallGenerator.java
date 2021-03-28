package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.GridData.ParameterTrim;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.GridData.NextMapTimeFromDate;
import com.severeweatheralerts.Graphics.Rounder;
import com.severeweatheralerts.Graphics.GridData.SumCalculator;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.TextUtils.Plurality;

import java.util.Date;

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
    layers.add(new Layer(new URL().getTotalSnow(bound, "conus", dateString)));
    layers.add(new Layer(new URL().getCountyMap(bound)));
    layers.add(new Layer(getZoneOverlay()));
    layers.add(new Layer(new URL().getTotalSnowPoints(bound, "conus", dateString)));
  }

  @Override
  protected String getSubText() {
    double snowfallAmt = new Rounder(getSnowfallInches(), SNOWFALL_AMOUNT_DECIMAL_PLACES).getRounded();
    return snowfallAmt + new Plurality(snowfallAmt, " inch", " inches").getText();
  }

  private double getSnowfallInches() {
    ParameterTrim parameterTrim = new ParameterTrim(gridData);
    parameterTrim.trimLeft(new Date());
    parameterTrim.trimRight(alert.getEndTime());
    return new SumCalculator(parameterTrim.getTrimmed()).getSum() / 25.4;
  }
}
