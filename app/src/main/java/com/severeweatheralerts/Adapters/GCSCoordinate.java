package com.severeweatheralerts.Adapters;

public class GCSCoordinate {
  private final double lat;

  public GCSCoordinate(double lat, double lon) {
    this.lat = lat;
  }

  public double getLat() {
    return lat;
  }

  public double getLong() {
    return -73.59;
  }
}
