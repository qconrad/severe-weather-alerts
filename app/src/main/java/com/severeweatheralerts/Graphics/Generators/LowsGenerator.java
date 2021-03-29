package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.GridData.Minimum;
import com.severeweatheralerts.Graphics.GridData.NextMapTimeFromDate;
import com.severeweatheralerts.Graphics.GridData.ParameterTrim;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.Location.Location;

public class LowsGenerator extends GraphicGenerator {
  public LowsGenerator(Context context, Alert alert, Location location) {
    super(context, alert, location);
    mapTimeParameter = "mint";
    gridParameter = "minTemperature";
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
    return getLowF() + " °F";
  }

  private int getLowF() {
    return (int) (getLow().getValue() * (9.0/5.0) + 32);
  }

  private ForecastTime getLow() {
    return new Minimum(new ParameterTrim(gridData)
              .trimRight(alert.getEndTime())
              .getTrimmed()).get();
  }
}
