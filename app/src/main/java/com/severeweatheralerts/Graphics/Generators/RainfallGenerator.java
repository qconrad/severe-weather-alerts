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

import static com.severeweatheralerts.Constants.RAINFALL_AMOUNT_DECIMAL_PLACES;

public class RainfallGenerator extends GraphicGenerator {
  public RainfallGenerator(Context context, Alert alert, Location location) {
    super(context, alert, location);
    mapTimeParameter = "totalqpf";
    gridParameter = "quantitativePrecipitation";
  }

  @Override
  protected void getURLs() {
    String dateString = new NextMapTimeFromDate(mapTimes, alert.getEndTime()).getMapTime().getString();
    layers.add(new Layer(new URL().getTotalRain(bound, "conus", dateString)));
    layers.add(new Layer(new URL().getCountyMap(bound)));
    layers.add(new Layer(getZoneOverlay()));
    layers.add(new Layer(new URL().getTotalRainPoints(bound, "conus", dateString)));
  }

  @Override
  protected String getSubText() {
    double rainfallAmt = new Rounder(getRainfallInches(), RAINFALL_AMOUNT_DECIMAL_PLACES).getRounded();
    return rainfallAmt + new Plurality(rainfallAmt, " inch", " inches").getText();
  }

  private double getRainfallInches() {
    ParameterTrim parameterTrim = new ParameterTrim(gridData);
    parameterTrim.trimLeft(new Date());
    parameterTrim.trimRight(alert.getEndTime());
    return new SumCalculator(parameterTrim.getTrimmed()).getSum() / 25.4;
  }
}
