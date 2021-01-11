package com.severeweatheralerts.TimeFormatters;

import java.util.Date;

public class RelativeTimeFormatter extends TimeFormatter {
  public RelativeTimeFormatter(Date first, Date second) {
    super(first, second);
  }

  @Override
  public String getFormattedString() {
    long differenceMs = getDifference();
    double seconds = toSeconds(differenceMs);
    int minutes = getMinutes(seconds);
    int hours = getHours(seconds);
    int days = getDays(seconds);
    if (days(days)) return days + " days";
    if (day(days, hours)) return "1 day";
    if (hours(hours)) return hours + " hours";
    if (hour(minutes, hours)) return "1 hour";
    if (minutes(minutes)) return minutes + " minutes";
    return "1 minute";
  }

  private double toSeconds(long difference) {
    return difference / 1000.0;
  }

  private int getDays(double seconds) {
    return (int) Math.round(seconds / 60 / 60 / 24);
  }

  private int getHours(double seconds) {
    return (int) Math.round(seconds / 60 / 60);
  }

  private int getMinutes(double seconds) {
    return (int) Math.round(seconds / 60.0);
  }

  private boolean minutes(int minutes) {
    return minutes > 1;
  }

  private boolean hour(int minutes, int hours) {
    return hours == 1 && minutes >= 60;
  }

  private boolean hours(int hours) {
    return hours > 1;
  }

  private boolean days(int days) {
    return days > 1;
  }

  private boolean day(int days, int hours) {
    return days == 1 && hours >= 24;
  }
}
