package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.GridData.Maximum;
import com.severeweatheralerts.Graphics.GridData.ParemeterTrim;
import com.severeweatheralerts.Graphics.NextMapTimeFromDate;
import com.severeweatheralerts.Graphics.Rounder;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.TextUtils.Plurality;

public class WindGustsGenerator extends GraphicGenerator {
  public WindGustsGenerator(Context context, Alert alert, Location location) {
    super(context, alert, location);
    mapTimeParameter = "windgust";
    gridParameter = "windGust";
  }

  @Override
  protected void getURLs() {
    String dateString = new NextMapTimeFromDate(mapTimes, getMaxGust().getDate()).getMapTime().getString();
    urls.add(new URL().getWindGusts(bound, "conus", dateString));
    urls.add(new URL().getWindGustsPoints(bound, "conus", dateString));
    urls.add(new URL().getCountyMap(bound));
  }

  @Override
  protected String getSubText() {
    return getWindGustsMph() + " mph";
  }

  private int getWindGustsMph() {
    return (int) (getMaxGust().getValue() / 1.609);
  }

  private ForecastTime maxGust = null;
  private ForecastTime getMaxGust() {
    if (maxGust == null)
      maxGust = new Maximum(new ParemeterTrim(gridData)
              .trimLeft(alert.getStartTime())
              .trimRight(alert.getEndTime())
              .getTrimmed())
              .get();
    return maxGust;
  }
}
