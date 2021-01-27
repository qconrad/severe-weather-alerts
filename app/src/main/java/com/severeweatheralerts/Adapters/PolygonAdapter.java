package com.severeweatheralerts.Adapters;

import com.severeweatheralerts.Graphics.MercatorCoordinateAdapter;
import com.severeweatheralerts.Graphics.Polygon;

public class PolygonAdapter {
  public static Polygon toMercatorPolygon(GeoJSONPolygon geoJSONPolygon) {
    Polygon polygon = new Polygon();
    for (int i = 0; i < geoJSONPolygon.getCoordinateCount(); i++)
      polygon.addCoordinate(new MercatorCoordinateAdapter(geoJSONPolygon.getCoordinate(i)).getCoordinate());
    return polygon;
  }
}
