package com.severeweatheralerts.Graphics.Polygon;

import com.severeweatheralerts.Adapters.GCSCoordinate;

import static com.severeweatheralerts.Adapters.CoordinateAdapter.lat2y;
import static com.severeweatheralerts.Adapters.CoordinateAdapter.lon2x;

public class GCSToMercatorCoordinateAdapter {
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
}
