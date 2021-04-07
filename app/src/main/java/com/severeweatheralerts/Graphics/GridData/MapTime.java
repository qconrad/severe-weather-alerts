package com.severeweatheralerts.Graphics.GridData;

import java.util.Date;

public class MapTime {
  private final String dateString;
  private final Date date;

  public MapTime(Date date, String dateString) {
    this.dateString = dateString;
    this.date = date;
  }

  public String getString() {
    return dateString;
  }

  public Date getDate() {
    return date;
  }
}
