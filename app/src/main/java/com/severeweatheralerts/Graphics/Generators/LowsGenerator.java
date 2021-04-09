package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.GridData.Minimum;
import com.severeweatheralerts.Graphics.GridData.NextMapTimeFromDate;
import com.severeweatheralerts.Graphics.GridData.Parameter;
import com.severeweatheralerts.Graphics.GridData.ParameterTrim;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.URL;

import static com.severeweatheralerts.Graphics.UnitConverter.cToF;

public class LowsGenerator extends GraphicGenerator {
  private ForecastTime expectedLow;

  public LowsGenerator(Context context, Alert alert, GCSCoordinate location) {
    super(context, alert, location);
    mapTimeParameter = "mint";
    gridParameter = "minTemperature";
  }

  @Override
  protected void gridDataAvailable(Parameter gridData) {
    expectedLow = getLow(gridData);
  }

  @Override
  protected void getURLs() {
    String dateString = new NextMapTimeFromDate(mapTimes, alert.getStartTime()).getMapTime().getString();
    layers.add(new Layer(new URL().getLows(bound, getRegion(), dateString)));
    layers.add(new Layer(new URL().getCountyMap(bound)));
    layers.add(new Layer(getZoneOverlay()));
    layers.add(new Layer(new URL().getLowsPoints(bound, getRegion(), dateString)));
  }

  @Override
  protected String getSubText() {
    return Math.round(cToF(expectedLow.getValue())) + "°F";
  }

  private ForecastTime getLow(Parameter gridData) {
    return new Minimum(new ParameterTrim(gridData)
              .trimRight(alert.getEndTime())
              .getTrimmed()).get();
  }
}
