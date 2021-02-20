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
    if (isMultiPolygon()) parseMultipolygon();
    else parsePolygon();
    return polygons;
  }

  private void parsePolygon() throws JSONException {
    addPolygon(getGeometryData());
  }

  private void parseMultipolygon() throws JSONException {
    for (int currentPolygon = 0; currentPolygon < getGeometryData().length(); currentPolygon++)
      addPolygon(getGeometryData().getJSONArray(currentPolygon));
  }

  private void addPolygon(JSONArray polygon) throws JSONException {
    GeoJSONPolygon geoJSONPolygon = new GeoJSONPolygon();
    JSONArray coordinates = polygon.getJSONArray(0);
    for (int i = 0; i < coordinates.length(); i++)
      geoJSONPolygon.addCoordinate(new GCSCoordinate(
              getLat(coordinates.getJSONArray(i)),
              getLon(coordinates.getJSONArray(i))));
    polygons.add(geoJSONPolygon);
  }

  private boolean isMultiPolygon() throws JSONException {
    return geometry.get("type").equals("MultiPolygon");
  }

  private JSONArray getGeometryData() throws JSONException {
    return geometry.getJSONArray("coordinates");
  }

  private double getLon(JSONArray coordinate) throws JSONException {
    return coordinate.getDouble(0);
  }

  private double getLat(JSONArray coordinate) throws JSONException {
    return coordinate.getDouble(1);
  }
}
