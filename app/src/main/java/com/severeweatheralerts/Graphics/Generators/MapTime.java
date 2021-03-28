package com.severeweatheralerts.Graphics.Generators;

import com.severeweatheralerts.TextUtils.DateTimeConverter;

import java.util.Date;
import java.util.TimeZone;

public class MapTime {
  private final String dateString;
  private final Date date;

  public MapTime(String dateString) {
    this.dateString = dateString;
    this.date = DateTimeConverter.convertStringToDate(dateString, "yyyy-MM-dd'T'HH:mm", TimeZone.getTimeZone("UTC"));
  }

  public String getString() {
    return dateString;
  }

  public Date getDate() {
    return date;
  }
}
