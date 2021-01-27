package com.severeweatheralerts;

import com.severeweatheralerts.JSONParsing.GeometryParser;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GeometryParserTests {
  String singlePolygonGeometry = "{\"type\":\"Polygon\",\"coordinates\":[[[-73.59,44.26],[-73.31,44.46],[-72.9,44.37],[-73.36,44],[-73.59,44.26]]]}";
  @Test
  public void parseAlerts_GeometryGiven_FirstCoordLatitudeParsed() throws JSONException {
    GeometryParser geometryParser = new GeometryParser(new JSONObject(singlePolygonGeometry));
    assertEquals(44.25, geometryParser.parseGeometry().get(0).getCoordinate(0).getLat(), 0.01);
  }

  @Test
  public void parseAlerts_GeometryGiven_FirstCoordLongitudeParsed() throws JSONException {
    GeometryParser geometryParser = new GeometryParser(new JSONObject(singlePolygonGeometry));
    assertEquals(-73.59, geometryParser.parseGeometry().get(0).getCoordinate(0).getLong(), 0.01);
  }

  @Test
  public void parseAlerts_GeometryGiven_SecondCoordLatitudeCorrect() throws JSONException {
    GeometryParser geometryParser = new GeometryParser(new JSONObject(singlePolygonGeometry));
    assertEquals(44.46, geometryParser.parseGeometry().get(0).getCoordinate(1).getLat(), 0.01);
  }

  @Test
  public void parseAlerts_GeometryGiven_SecondCoordLongitudeCorrect() throws JSONException {
    GeometryParser geometryParser = new GeometryParser(new JSONObject(singlePolygonGeometry));
    assertEquals(-73.31, geometryParser.parseGeometry().get(0).getCoordinate(1).getLong(), 0.01);
  }

  @Test
  public void parseAlerts_GeometryGiven_ThirdCoordLatitudeCorrect() throws JSONException {
    GeometryParser geometryParser = new GeometryParser(new JSONObject(singlePolygonGeometry));
    assertEquals(44.37, geometryParser.parseGeometry().get(0).getCoordinate(2).getLat(), 0.01);
  }
}
