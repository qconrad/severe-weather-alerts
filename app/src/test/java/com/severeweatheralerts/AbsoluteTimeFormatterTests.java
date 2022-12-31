package com.severeweatheralerts;

import static org.junit.Assert.assertEquals;

import com.severeweatheralerts.TimeFormatters.AbsoluteTimeFormatter;

import org.junit.Test;

import java.util.Date;
import java.util.TimeZone;

public class AbsoluteTimeFormatterTests {
  @Test
  public void getFormattedString_DateProvided_CorrectTime() {
    AbsoluteTimeFormatter absoluteTimeFormatter = new AbsoluteTimeFormatter(new Date(0L), new Date(154800000L), TimeZone.getTimeZone("CST"));
    assertEquals("1 PM Friday", absoluteTimeFormatter.getFormattedString());
  }

  @Test
  public void getFormattedString_DifferentDateProvided_CorrectTime() {
    AbsoluteTimeFormatter absoluteTimeFormatter = new AbsoluteTimeFormatter(new Date(0L), new Date(93600000L), TimeZone.getTimeZone("CST"));
    assertEquals("8 PM Thursday", absoluteTimeFormatter.getFormattedString());
  }

  @Test
  public void getFormattedString_DateTomorrowAdded_CorrectTime() {
    AbsoluteTimeFormatter absoluteTimeFormatter = new AbsoluteTimeFormatter(new Date(0), new Date(154800000L), TimeZone.getTimeZone("CST"));
    assertEquals("1 PM Friday", absoluteTimeFormatter.getFormattedString());
  }

  @Test
  public void getFormattedString_DateThreeDaysAway_DayOfWeekReturned() {
    AbsoluteTimeFormatter absoluteTimeFormatter = new AbsoluteTimeFormatter(new Date(0), new Date(313200000L), TimeZone.getTimeZone("CST"));
    assertEquals("9 AM Sunday", absoluteTimeFormatter.getFormattedString());
  }

  @Test
  public void getFormattedString_DifferentTimeZoneProvided_CorrectTimeReturn() {
    AbsoluteTimeFormatter absoluteTimeFormatter = new AbsoluteTimeFormatter(new Date(0), new Date(313200000L), TimeZone.getTimeZone("EST"));
    assertEquals("10 AM Sunday", absoluteTimeFormatter.getFormattedString());
  }

  @Test
  public void getFormattedString_12PMProvided_ChangedToNoon() {
    AbsoluteTimeFormatter absoluteTimeFormatter = new AbsoluteTimeFormatter(new Date(0L), new Date(154800000L), TimeZone.getTimeZone("MST"));
    assertEquals("noon Friday", absoluteTimeFormatter.getFormattedString());
  }

  @Test
  public void getFormattedString_12AMProvided_ChangedToMidnight() {
    AbsoluteTimeFormatter absoluteTimeFormatter = new AbsoluteTimeFormatter(new Date(0L), new Date(111600000L), TimeZone.getTimeZone("MST"));
    assertEquals("midnight Friday", absoluteTimeFormatter.getFormattedString());
  }

  @Test
  public void getFormattedString_TimeNotOnHourProvided_ShowsMinutes() {
    AbsoluteTimeFormatter absoluteTimeFormatter = new AbsoluteTimeFormatter(new Date(0L), new Date(1613362935000L), TimeZone.getTimeZone("CST"));
    assertEquals("10:22 PM Sunday", absoluteTimeFormatter.getFormattedString());
  }

  @Test
  public void getFormattedString_NearTimeNotOnHourProvided_ShowsMinutes() {
    AbsoluteTimeFormatter absoluteTimeFormatter = new AbsoluteTimeFormatter(new Date(0L), new Date(9900000L), TimeZone.getTimeZone("CST"));
    assertEquals("8:45 PM", absoluteTimeFormatter.getFormattedString());
  }

  // use 24 hour time if setTimeFormat24Hour(true) is called
  @Test
  public void getFormattedString_24HourTimeProvided_Shows24HourTime() {
    AbsoluteTimeFormatter absoluteTimeFormatter = new AbsoluteTimeFormatter(new Date(0L), new Date(154800000L), TimeZone.getTimeZone("CST"));
    absoluteTimeFormatter.setTimeFormat24Hour(true);
    assertEquals("13:00 Friday", absoluteTimeFormatter.getFormattedString());
  }

  // 24 hour time without day of week (12 hours out)
  @Test
  public void getFormattedString_24HourTimeNear12HoursProvided_Shows24HourTime() {
    AbsoluteTimeFormatter absoluteTimeFormatter = new AbsoluteTimeFormatter(new Date(0L), new Date(9900000L), TimeZone.getTimeZone("CST"));
    absoluteTimeFormatter.setTimeFormat24Hour(true);
    assertEquals("20:45", absoluteTimeFormatter.getFormattedString());
  }

  // setTimeFormat24Hour() returns itself so that it can be chained
  @Test
  public void getFormattedString_Chained24HourTimeProvided_Shows24HourTime() {
    AbsoluteTimeFormatter absoluteTimeFormatter = new AbsoluteTimeFormatter(new Date(0L), new Date(154800000L), TimeZone.getTimeZone("CST"));
    assertEquals("13:00 Friday", absoluteTimeFormatter.setTimeFormat24Hour(true).getFormattedString());
  }
}
