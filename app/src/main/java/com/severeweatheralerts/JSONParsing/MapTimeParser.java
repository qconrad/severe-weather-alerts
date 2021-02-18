package com.severeweatheralerts.JSONParsing;

import com.severeweatheralerts.MapTime;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MapTimeParser {
  private JSONArray dateTimeJson = null;
  private ArrayList<MapTime> mapTimes;

  public MapTimeParser(String times) {
    try { parseMapTimes(times); }
    catch (JSONException e) { e.printStackTrace(); }
  }

  private void parseMapTimes(String times) throws JSONException {
    createJSONArray(times);
    convertTimes();
  }

  private void convertTimes() throws JSONException {
    mapTimes = new ArrayList<>();
    for (int i = 0; i < dateTimeJson.length(); i++) addMapTime(i);
  }

  private void addMapTime(int i) throws JSONException {
    mapTimes.add(new MapTime(getDateString(i)));
  }

  private String getDateString(int i) throws JSONException {
    return dateTimeJson.getJSONArray(i).getString(0);
  }

  private void createJSONArray(String times) throws JSONException {
    this.dateTimeJson = new JSONArray(times);
  }

  public ArrayList<MapTime> getMapTimes() {
    return mapTimes;
  }
}
