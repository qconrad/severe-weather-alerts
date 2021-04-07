package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.GridData.Maximum;
import com.severeweatheralerts.Graphics.GridData.Parameter;
import com.severeweatheralerts.Graphics.GridData.ParameterTrim;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.GridData.NextMapTimeFromDate;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.Location.Location;

import java.util.Date;

import static com.severeweatheralerts.Graphics.UnitConverter.kphToMph;

public class WindGustsGenerator extends GraphicGenerator {
  private ForecastTime maxGust;

  public WindGustsGenerator(Context context, Alert alert, Location location) {
    super(context, alert, location);
    mapTimeParameter = "windgust";
    gridParameter = "windGust";
  }

  @Override
  protected void gridDataAvailable(Parameter gridData) {
    if (gridData.getCount() < 1) throwError("Gusts not available");
    maxGust = getMaxGust(gridData);
  }

  @Override
  protected void getURLs() {
    String dateString = new NextMapTimeFromDate(mapTimes, maxGust.getDate()).getMapTime().getString();
    layers.add(new Layer(new URL().getWindGusts(bound, getRegion(), dateString)));
    layers.add(new Layer(new URL().getCountyMap(bound)));
    layers.add(new Layer(getZoneOverlay()));
    layers.add(new Layer(new URL().getWindGustsPoints(bound, getRegion(), dateString)));
  }

  @Override
  protected String getSubText() {
    return Math.round(kphToMph(maxGust.getValue())) + " mph";
  }

  private ForecastTime getMaxGust(Parameter gridData) {
    return new Maximum(new ParameterTrim(gridData)
              .trimLeft(new Date())
              .trimRight(alert.getEndTime())
              .getTrimmed())
              .get();
  }
}
