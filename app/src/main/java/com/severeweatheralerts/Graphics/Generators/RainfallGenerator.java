package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.GridData.Parameter;
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
  private double rainfallAmount;

  public RainfallGenerator(Context context, Alert alert, Location location) {
    super(context, alert, location);
    mapTimeParameter = "totalqpf";
    gridParameter = "quantitativePrecipitation";
  }

  @Override
  protected void gridDataAvailable(Parameter gridData) {
    rainfallAmount = new Rounder(mmToIn(getRainfall(gridData)), RAINFALL_AMOUNT_DECIMAL_PLACES).getRounded();
  }

  @Override
  protected void getURLs() {
    String dateString = new NextMapTimeFromDate(mapTimes, alert.getEndTime()).getMapTime().getString();
    layers.add(new Layer(new URL().getTotalRain(bound, getRegion(), dateString)));
    layers.add(new Layer(new URL().getCountyMap(bound)));
    layers.add(new Layer(getZoneOverlay()));
    layers.add(new Layer(new URL().getTotalRainPoints(bound, getRegion(), dateString)));
  }

  @Override
  protected String getSubText() {
    return rainfallAmount + new Plurality(rainfallAmount, " inch", " inches").getText();
  }

  private double mmToIn(double value) {
    return value / 25.4;
  }

  private double getRainfall(Parameter gridData) {
    return new SumCalculator(new ParameterTrim(gridData)
              .trimLeft(new Date())
              .trimRight(alert.getEndTime())
              .getTrimmed())
              .getSum();
  }
}
