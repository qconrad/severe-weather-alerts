package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.GridData.Minimum;
import com.severeweatheralerts.Graphics.GridData.Parameter;
import com.severeweatheralerts.Graphics.GridData.ParameterTrim;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.GridData.NextMapTimeFromDate;
import com.severeweatheralerts.Graphics.URL;

import java.util.Date;

import static com.severeweatheralerts.Graphics.UnitConverter.cToF;

public class WindChillGenerator extends GraphicGenerator {
  private ForecastTime minWindChill;

  public WindChillGenerator(Context context, Alert alert, GCSCoordinate location) {
    super(context, alert, location);
    mapTimeParameter = "apparentt";
    gridParameter = "apparentTemperature";
  }

  @Override
  protected void gridDataAvailable(Parameter gridData) {
    if (gridData.getCount() < 1) throwError("Wind chills not available");
    minWindChill = getMinimumWindChill(gridData);
  }

  @Override
  protected void getURLs() {
    String dateString = new NextMapTimeFromDate(mapTimes, minWindChill.getDate()).getMapTime().getString();
    layers.add(new Layer(new URL().getApparentTemperature(bound, getRegion(), dateString)));
    layers.add(new Layer(new URL().getCountyMap(bound)));
    layers.add(new Layer(getZoneOverlay()));
    layers.add(new Layer(new URL().getApparentTemperaturePoints(bound, getRegion(), dateString)));
  }

  @Override
  protected String getSubText() {
    return Math.round(cToF(minWindChill.getValue())) + "°F";
  }

  private ForecastTime getMinimumWindChill(Parameter gridData) {
    return new Minimum(new ParameterTrim(gridData)
            .trimLeft(new Date())
            .trimRight(alert.getEndTime())
            .getTrimmed())
            .get();
  }
}
