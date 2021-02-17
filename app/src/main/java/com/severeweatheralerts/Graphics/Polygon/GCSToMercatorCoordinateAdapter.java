package com.severeweatheralerts.Graphics.Polygon;

import com.severeweatheralerts.Adapters.GCSCoordinate;

public class GCSToMercatorCoordinateAdapter {
  final static double RADIUS = 6378137.0;
  private final GCSCoordinate gcsCoordinate;
  MercatorCoordinate mercatorCoordinate;

  public GCSToMercatorCoordinateAdapter(GCSCoordinate gcsCoordinate) {
    this.gcsCoordinate = gcsCoordinate;
    adaptCoordinate();
  }

  public MercatorCoordinate getCoordinate() {
    return mercatorCoordinate;
  }

  private void adaptCoordinate() {
    mercatorCoordinate = new MercatorCoordinate(getX(), getY());
  }

  private double getX() {
    return lon2x(gcsCoordinate.getLong());
  }

  private double getY() {
    return lat2y(gcsCoordinate.getLat());
  }

  private static double lat2y(double aLat) {
    return Math.log(Math.tan(Math.PI / 4 + Math.toRadians(aLat) / 2)) * RADIUS;
  }

  private static double lon2x(double aLong) {
    return Math.toRadians(aLong) * RADIUS;
  }
}
