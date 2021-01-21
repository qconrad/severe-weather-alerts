package com.severeweatheralerts.Adapters;

import java.util.ArrayList;

public class GeoJSONPolygon {
  private final ArrayList<GCSCoordinate> coordinates = new ArrayList<>();

  public GCSCoordinate getCoordinate(int index) {
    return coordinates.get(index);
  }

  public void addCoordinate(GCSCoordinate coordinate) {
    coordinates.add(coordinate);
  }

  public int getCoordinateCount() {
    return coordinates.size();
  }
}