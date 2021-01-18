package com.severeweatheralerts;

import com.severeweatheralerts.TimeFormatters.AbsoluteTimeFormatter;

import org.junit.Test;

import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

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
}
