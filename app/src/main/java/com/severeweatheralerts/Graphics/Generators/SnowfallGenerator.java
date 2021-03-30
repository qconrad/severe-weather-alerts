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

import static com.severeweatheralerts.Constants.SNOWFALL_AMOUNT_DECIMAL_PLACES;
import static com.severeweatheralerts.Graphics.UnitConverter.mmToIn;

public class SnowfallGenerator extends GraphicGenerator {
  private double snowfallAmount;

  public SnowfallGenerator(Context context, Alert alert, Location location) {
    super(context, alert, location);
    mapTimeParameter = "snowamt";
    gridParameter = "snowfallAmount";
  }

  @Override
  protected void gridDataAvailable(Parameter gridData) {
    snowfallAmount = new Rounder(mmToIn(getSnowfall(gridData)), SNOWFALL_AMOUNT_DECIMAL_PLACES).getRounded();
  }

  @Override
  protected void getURLs() {
    String dateString = new NextMapTimeFromDate(mapTimes, alert.getEndTime()).getMapTime().getString();
    layers.add(new Layer(new URL().getTotalSnow(bound, getRegion(), dateString)));
    layers.add(new Layer(new URL().getCountyMap(bound)));
    layers.add(new Layer(getZoneOverlay()));
    layers.add(new Layer(new URL().getTotalSnowPoints(bound, getRegion(), dateString)));
  }

  @Override
  protected String getSubText() {
    return snowfallAmount + new Plurality(snowfallAmount, " inch", " inches").getText();
  }

  private double getSnowfall(Parameter gridData) {
    return new SumCalculator(new ParameterTrim(gridData)
              .trimLeft(new Date())
              .trimRight(alert.getEndTime())
              .getTrimmed())
              .getSum();
  }
}
