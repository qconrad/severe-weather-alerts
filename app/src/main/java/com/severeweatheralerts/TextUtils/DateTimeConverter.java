package com.severeweatheralerts.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeConverter {
  public static Date convertStringToDate(String dateTimeString) {
    return convertStringToDate(dateTimeString, "yyyy-MM-dd'T'HH:mm:ssZ", null);
  }

  public static Date convertStringToDate(String dateTimeString, String format, TimeZone timeZone) {
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    formatter.setTimeZone(timeZone);
    try { return formatter.parse(dateTimeString); }
    catch(Exception e) { return null; }
  }

  public static String convertDateToString(Date date, String format, TimeZone timeZone) {
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    formatter.setTimeZone(timeZone);
    return formatter.format(date);
  }
}
