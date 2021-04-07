package com.severeweatheralerts.JSONParsing;

import com.severeweatheralerts.Graphics.GridData.MapTime;
import com.severeweatheralerts.TextUtils.DateTimeConverter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class MapTimeParser {
  protected JSONArray dateTimeJson = null;
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
    mapTimes.add(new MapTime(getDateFromString(getDateString(i)), getDateString(i)));
  }

  protected Date getDateFromString(String string) {
    return DateTimeConverter.convertStringToDate(string, "yyyy-MM-dd'T'HH:mm", TimeZone.getTimeZone("UTC"));
  }

  protected String getDateString(int i) throws JSONException {
    return dateTimeJson.getJSONArray(i).getString(0);
  }

  private void createJSONArray(String times) throws JSONException {
    this.dateTimeJson = new JSONArray(times);
  }

  public ArrayList<MapTime> getMapTimes() {
    return mapTimes;
  }
}
