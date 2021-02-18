package com.severeweatheralerts;

import com.severeweatheralerts.TextUtils.DateTimeConverter;

import java.util.Date;

public class MapTime {
  private final String dateString;
  private final Date date;

  public MapTime(String dateString) {
    this.dateString = dateString;
    this.date = DateTimeConverter.convertStringToDate(dateString, "yyyy-MM-dd'T'HH:mm");
  }

  public String getString() {
    return dateString;
  }

  public Date getDate() {
    return date;
  }
}
