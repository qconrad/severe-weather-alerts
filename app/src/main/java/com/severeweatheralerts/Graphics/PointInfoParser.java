package com.severeweatheralerts.Graphics;

import org.json.JSONException;
import org.json.JSONObject;

public class PointInfoParser {
  private JSONObject pointJson;
  private String forecastGridLink;

  public PointInfoParser(String pointInfoJson) {
    try { parseInfo(pointInfoJson); }
    catch (JSONException e) { e.printStackTrace(); }
  }

  private void parseInfo(String pointInfoJson) throws JSONException {
     pointJson = new JSONObject(pointInfoJson);
     forecastGridLink = pointJson.getJSONObject("properties").getString("forecastGridData");
  }

  public String getForecastGridLink() {
    return forecastGridLink;
  }
}
