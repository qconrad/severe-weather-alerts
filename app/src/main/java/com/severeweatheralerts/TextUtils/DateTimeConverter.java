package com.severeweatheralerts.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeConverter {
  public static Date convertStringToDate(String dateTimeString) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    try { return formatter.parse(dateTimeString); }
    catch(Exception e) { return null; }
  }
}
