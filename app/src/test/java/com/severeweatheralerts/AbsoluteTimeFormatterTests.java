package com.severeweatheralerts;

import com.severeweatheralerts.TimeFormatters.AbsoluteTimeFormatter;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AbsoluteTimeFormatterTests {
  @Test
  public void getFormattedScreen_DateProvided_CorrectTime() {
    AbsoluteTimeFormatter absoluteTimeFormatter = new AbsoluteTimeFormatter(new Date(0L), new Date(90000000L));
    assertEquals("7 PM Today", absoluteTimeFormatter.getFormattedString());
  }

  @Test
  public void getFormattedScreen_DifferentDateProvided_CorrectTime() {
    AbsoluteTimeFormatter absoluteTimeFormatter = new AbsoluteTimeFormatter(new Date(39600000L), new Date(93600000L));
    assertEquals("8 PM Today", absoluteTimeFormatter.getFormattedString());
  }

  @Test
  public void getFormattedScreen_DateTomorrowAdded_CorrectTime() {
    AbsoluteTimeFormatter absoluteTimeFormatter = new AbsoluteTimeFormatter(new Date(0), new Date(162000000L));
    assertEquals("3 PM Tomorrow", absoluteTimeFormatter.getFormattedString());
  }

  @Test
  public void getFormattedScreen_DateThreeDaysAway_DayOfWeekReturned() {
    AbsoluteTimeFormatter absoluteTimeFormatter = new AbsoluteTimeFormatter(new Date(0), new Date(334800000L));
    assertEquals("3 PM Sunday", absoluteTimeFormatter.getFormattedString());
  }
}
