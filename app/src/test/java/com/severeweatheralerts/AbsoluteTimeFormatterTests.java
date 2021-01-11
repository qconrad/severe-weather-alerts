package com.severeweatheralerts;

import com.severeweatheralerts.TimeFormatters.AbsoluteTimeFormatter;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AbsoluteTimeFormatterTests {
  @Test
  public void getFormattedScreen_DateProvided_CorrectTime() {
    AbsoluteTimeFormatter absoluteTimeFormatter = new AbsoluteTimeFormatter(new Date(0L), new Date(1610393607000L));
    assertEquals("1 PM Today", absoluteTimeFormatter.getFormattedString());
  }

  @Test
  public void getFormattedScreen_DifferentDateProvided_CorrectTime() {
    AbsoluteTimeFormatter absoluteTimeFormatter = new AbsoluteTimeFormatter(new Date(0L), new Date(1610397207000L));
    assertEquals("2 PM Today", absoluteTimeFormatter.getFormattedString());
  }
}
