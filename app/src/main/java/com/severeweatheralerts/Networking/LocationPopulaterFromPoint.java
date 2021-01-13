package com.severeweatheralerts.Networking;

import com.severeweatheralerts.Location;

public class LocationPopulaterFromPoint extends LocationPopulater {
  public LocationPopulaterFromPoint(Location location) {
    super(location);
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
