package com.severeweatheralerts.Networking.LocationPopulaters;

import android.content.Context;

import com.severeweatheralerts.Location.Location;

public class FromLocationPointPopulater extends AllNWSPopulater {
  public FromLocationPointPopulater(Location location, Context context) {
    super(location, context);
  }

  protected String getUrl() {
    return "https://api.weather.gov/alerts?point=" + getLat() + "%2C" + getLong() + "&status=actual";
  }

  private String getLat() {
    return String.valueOf(location.getLatitude());
  }

  private String getLong() {
    return String.valueOf(location.getLongitude());
  }
}
