package com.severeweatheralerts.JSONParsing;

import com.severeweatheralerts.TextUtils.DateTimeConverter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

public class MapTimeParser {
  private JSONArray dateTimeJson = null;
  private ArrayList<Date> dates;
  private ArrayList<String> strings;

  public MapTimeParser(String times) {
    try { parseMapTimes(times); }
    catch (JSONException e) { e.printStackTrace(); }
  }

  private void parseMapTimes(String times) throws JSONException {
    createJSONArray(times);
    convertTimes();
  }

  private void convertTimes() throws JSONException {
    dates = new ArrayList<>();
    strings = new ArrayList<>();
    for (int i = 0; i < dateTimeJson.length(); i++) addDate(i);
  }

  private void addDate(int i) throws JSONException {
    strings.add(getDateString(i));
    dates.add(DateTimeConverter.convertStringToDate(getDateString(i), getDateFormat()));
  }

  private String getDateString(int i) throws JSONException {
    return dateTimeJson.getJSONArray(i).getString(0);
  }

  protected String getDateFormat() {
    return "yyyy-MM-dd'T'HH:mm";
  }

  private void createJSONArray(String times) throws JSONException {
    this.dateTimeJson = new JSONArray(times);
  }

  public ArrayList<Date> getDates() {
    return dates;
  }

  public ArrayList<String> getDateStrings() {
    return strings;
  }
}
