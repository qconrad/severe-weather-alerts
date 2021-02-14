package com.severeweatheralerts.Graphics;

import com.severeweatheralerts.TextUtils.DateTimeConverter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

public class MapTimeParser {
  private JSONArray dateTimeJson = null;
  private ArrayList<Date> dates;

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
    for (int i = 0; i < dateTimeJson.length(); i++) addDate(i);
  }

  private void addDate(int i) throws JSONException {
    dates.add(DateTimeConverter.convertStringToDate(dateTimeJson.getJSONArray(i).getString(1), getDateFormat()));
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
}
