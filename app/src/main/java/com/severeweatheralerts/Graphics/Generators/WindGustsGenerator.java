package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.GridData.Maximum;
import com.severeweatheralerts.Graphics.GridData.Parameter;
import com.severeweatheralerts.Graphics.GridData.ParemeterTrim;
import com.severeweatheralerts.Graphics.NextMapTimeFromDate;
import com.severeweatheralerts.Location.Location;

import java.util.Date;

public class WindGustsGenerator extends GraphicGenerator {
  public WindGustsGenerator(Context context, Alert alert, Location location) {
    super(context, alert, location);
    mapTimeParameter = "windgust";
    gridParameter = "windGust";
  }

  @Override
  protected void getURLs() {
    Parameter trimmed = new ParemeterTrim(gridData).trimLeft(alert.getStartTime()).trimRight(alert.getEndTime()).getTrimmed();
    ForecastTime forecastTime = new Maximum(trimmed).get();
    String dateString = new NextMapTimeFromDate(mapTimes, forecastTime.getDate()).getMapTime().getString();
    urls.add(new URL().getWindGusts(bound, "conus", dateString));
    urls.add(new URL().getWindGustsPoints(bound, "conus", dateString));
    urls.add(new URL().getCountyMap(bound));
  }
}
