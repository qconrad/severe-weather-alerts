package com.severeweatheralerts.JSONParsing;

import com.severeweatheralerts.Graphics.URLGeneration.ForecastTime;
import com.severeweatheralerts.Graphics.URLGeneration.GridDataParameter;
import com.severeweatheralerts.TextUtils.DateTimeConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class GridDataParser {
  JSONObject jsonObject;
  public GridDataParser(String snowGridData) throws JSONException {
     jsonObject = new JSONObject(snowGridData);
  }

  public GridDataParameter getParameter(String parameter) {
    GridDataParameter gridDataParameter = null;
    try { gridDataParameter = new GridDataParameter(getParam(parameter)); }
    catch (Exception ignored) {}
    return gridDataParameter;
  }

  private ArrayList<ForecastTime> getParam(String param) throws JSONException {
    ArrayList<ForecastTime> forecastTimes = new ArrayList<>();
    JSONArray values = getValues(param);
    for (int i = 0; i < values.length(); i++) addForecastTime(forecastTimes, values.getJSONObject(i));
    return forecastTimes;
  }

  private void addForecastTime(ArrayList<ForecastTime> forecastTimes, JSONObject value) throws JSONException {
    forecastTimes.add(new ForecastTime(getDate(value), getValue(value)));
  }

  private Date getDate(JSONObject value) throws JSONException {
    return DateTimeConverter.convertStringToDate(value.getString("validTime"));
  }

  private JSONArray getValues(String param) throws JSONException {
    return jsonObject.getJSONObject("properties").getJSONObject(param).getJSONArray("values");
  }

  private double getValue(JSONObject value) throws JSONException {
    return value.getDouble("value");
  }
}
