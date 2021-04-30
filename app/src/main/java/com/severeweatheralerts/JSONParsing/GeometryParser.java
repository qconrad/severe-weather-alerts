package com.severeweatheralerts.JSONParsing;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Adapters.GeoJSONPolygon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GeometryParser {
  private final JSONObject geometryInput;
  ArrayList<GeoJSONPolygon> polygons = new ArrayList<>();

  public GeometryParser(JSONObject geometry) {
    this.geometryInput = geometry;
  }

  public ArrayList<GeoJSONPolygon> parseGeometry() throws JSONException {
    if (isGeometryCollection(geometryInput)) parseGeometryCollection();
    else parseSingleGeometry(geometryInput);
    return polygons;
  }

  private void parseGeometryCollection() throws JSONException {
    for (int i = 0; i < geometryInput.getJSONArray("geometries").length(); i++)
      parseSingleGeometry(geometryInput.getJSONArray("geometries").getJSONObject(i));
  }

  private void parseSingleGeometry(JSONObject geometry) throws JSONException {
    if (isMultiPolygon(geometry)) parseMultipolygon(geometry);
    else parsePolygon(geometry);
  }

  private void parsePolygon(JSONObject geometry) throws JSONException {
    addPolygon(getGeometryData(geometry));
  }

  private void parseMultipolygon(JSONObject geometry) throws JSONException {
    for (int currentPolygon = 0; currentPolygon < getGeometryData(geometry).length(); currentPolygon++)
      addPolygon(getGeometryData(geometry).getJSONArray(currentPolygon));
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

  private boolean isMultiPolygon(JSONObject geometry) throws JSONException {
    return geometry.get("type").equals("MultiPolygon");
  }

  private boolean isGeometryCollection(JSONObject geometry) throws JSONException {
    return geometry.get("type").equals("GeometryCollection");
  }

  private JSONArray getGeometryData(JSONObject geometry) throws JSONException {
    return geometry.getJSONArray("coordinates");
  }

  private double getLon(JSONArray coordinate) throws JSONException {
    return coordinate.getDouble(0);
  }

  private double getLat(JSONArray coordinate) throws JSONException {
    return coordinate.getDouble(1);
  }
}
