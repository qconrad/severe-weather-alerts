package com.severeweatheralerts.Adapters;

import com.severeweatheralerts.Graphics.Polygon.GCSToMercatorCoordinateAdapter;
import com.severeweatheralerts.Graphics.Polygon.Polygon;

public class PolygonAdapter {
  public static Polygon toMercatorPolygon(GeoJSONPolygon geoJSONPolygon) {
    Polygon polygon = new Polygon();
    for (int i = 0; i < geoJSONPolygon.getCoordinateCount(); i++)
      polygon.addCoordinate(new GCSToMercatorCoordinateAdapter(geoJSONPolygon.getCoordinate(i)).getCoordinate());
    return polygon;
  }
}
