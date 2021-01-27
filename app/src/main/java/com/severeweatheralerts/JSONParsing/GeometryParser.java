package com.severeweatheralerts.JSONParsing;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Adapters.GeoJSONPolygon;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GeometryParser {
  private final JSONObject geometry;
  ArrayList<GeoJSONPolygon> polygons = new ArrayList<>();

  public GeometryParser(JSONObject geometry) {
    this.geometry = geometry;
  }

  public ArrayList<GeoJSONPolygon> parseGeometry() throws JSONException {
    GeoJSONPolygon geoJSONPolygon = new GeoJSONPolygon();
    for (int i = 0; i < geometry.getJSONArray("coordinates").getJSONArray(0).length(); i++)
      geoJSONPolygon.addCoordinate(new GCSCoordinate(getLat(i), getLon(i)));
    polygons.add(geoJSONPolygon);
    return polygons;
  }

  private double getLon(int i) throws JSONException {
    return geometry.getJSONArray("coordinates").getJSONArray(0).getJSONArray(i).getDouble(0);
  }

  private double getLat(int i) throws JSONException {
    return geometry.getJSONArray("coordinates").getJSONArray(0).getJSONArray(i).getDouble(1);
  }
}
