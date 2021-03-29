package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.GridData.Maximum;
import com.severeweatheralerts.Graphics.GridData.ParameterTrim;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.GridData.NextMapTimeFromDate;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.Location.Location;

public class WindGustsGenerator extends GraphicGenerator {
  public WindGustsGenerator(Context context, Alert alert, Location location) {
    super(context, alert, location);
    mapTimeParameter = "windgust";
    gridParameter = "windGust";
  }

  @Override
  protected void getURLs() {
    String dateString = new NextMapTimeFromDate(mapTimes, getMaxGust().getDate()).getMapTime().getString();
    layers.add(new Layer(new URL().getWindGusts(bound, getRegion(), dateString)));
    layers.add(new Layer(new URL().getCountyMap(bound)));
    layers.add(new Layer(getZoneOverlay()));
    layers.add(new Layer(new URL().getWindGustsPoints(bound, getRegion(), dateString)));
  }

  @Override
  protected String getSubText() {
    return getWindGustsMph() + " mph";
  }

  private int getWindGustsMph() {
    return (int) (getMaxGust().getValue() / 1.609);
  }

  private ForecastTime getMaxGust() {
    return new Maximum(new ParameterTrim(gridData)
              .trimLeft(alert.getStartTime())
              .trimRight(alert.getEndTime())
              .getTrimmed())
              .get();
  }
}
