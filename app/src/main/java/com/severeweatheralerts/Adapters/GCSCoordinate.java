package com.severeweatheralerts.Adapters;

public class GCSCoordinate {
  private final double lat;
  private final double lon;

  public GCSCoordinate(double lat, double lon) {
    this.lat = lat;
    this.lon = lon;
  }

  public double getLat() {
    return lat;
  }

  public double getLong() {
    return lon;
  }
}
