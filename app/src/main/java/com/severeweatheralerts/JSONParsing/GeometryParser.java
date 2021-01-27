package com.severeweatheralerts.JSONParsing;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Adapters.GeoJSONPolygon;

import org.json.JSONArray;
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
    for (int i = 0; i < getPolygon().length(); i++)
      geoJSONPolygon.addCoordinate(new GCSCoordinate(
              getLat(getPolygon().getJSONArray(i)),
              getLon(getPolygon().getJSONArray(i))));
    polygons.add(geoJSONPolygon);
    return polygons;
  }

  private JSONArray getPolygon() throws JSONException {
    JSONArray polygon = getCoordinates().getJSONArray(0);
    if (isMultiPolygon()) polygon = polygon.getJSONArray(0);
    return polygon;
  }

  private boolean isMultiPolygon() throws JSONException {
    return geometry.get("type").equals("MultiPolygon");
  }

  private JSONArray getCoordinates() throws JSONException {
    return geometry.getJSONArray("coordinates");
  }

  private double getLon(JSONArray coordinate) throws JSONException {
    return coordinate.getDouble(0);
  }

  private double getLat(JSONArray coordinate) throws JSONException {
    return coordinate.getDouble(1);
  }
}
