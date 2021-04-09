package com.severeweatheralerts.JSONParsing;

import com.severeweatheralerts.JSONParsing.MapTimeParser;

import org.json.JSONException;

import java.util.Date;

public class RadarMapTimeParser extends MapTimeParser {
  public RadarMapTimeParser(String times) {
    super(times);
  }

  protected String getDateString(int i) throws JSONException {
    return dateTimeJson.getString(i);
  }

  @Override
  protected Date getDateFromString(String string) {
    return new Date(Long.parseLong(string) * 1000L);
  }
}
