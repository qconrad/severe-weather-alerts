package com.severeweatheralerts.JSONParsing;

import org.json.JSONException;
import org.json.JSONObject;

public class PointInfoParser {
  private String forecastGridLink;
  private String radarStation;

  public PointInfoParser(String pointInfoJson) {
    try { parseInfo(pointInfoJson); }
    catch (JSONException e) { e.printStackTrace(); }
  }

  private void parseInfo(String pointInfoJson) throws JSONException {
    JSONObject pointJson = new JSONObject(pointInfoJson);
    forecastGridLink = pointJson.getJSONObject("properties").getString("forecastGridData");
    radarStation = pointJson.getJSONObject("properties").getString("radarStation");
  }

  public String getForecastGridLink() {
    return forecastGridLink;
  }

  public String getRadarStation() {
    return radarStation;
  }
}
