package com.severeweatheralerts;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TimeStringTests {
  @Test
  public void testRelativeTime_TwoDateObjectsGivenDifferenceIs30seconds_DifferenceStringIs1Minute() {
    Date firstDate = new Date();
    firstDate.setTime(1577836800000L); // 01/01/2020 12:00:00 AM
    Date secondDate = new Date();
    secondDate.setTime(1577836830000L); // 01/01/2020 12:00:30 AM
    RelativeTimeFormatter rtf = new RelativeTimeFormatter(firstDate, secondDate);
    String output = rtf.getFormattedString();
    assertEquals("1 minute", output);
  }

  @Test
  public void testRelativeTime_TwoDateObjectsGivenDifferenceIs5minutes_DifferenceStringIs5Minutes() {
    Date firstDate = new Date();
    firstDate.setTime(1577836800000L); // 01/01/2020 12:00:00 AM
    Date secondDate = new Date();
    secondDate.setTime(1577837100000L); // 01/01/2020 12:05:00 AM
    RelativeTimeFormatter rtf = new RelativeTimeFormatter(firstDate, secondDate);
    String output = rtf.getFormattedString();
    assertEquals("5 minutes", output);
  }

  @Test
  public void testRelativeTime_TwoDateObjectsGivenDifferenceIs59minutes_DifferenceStringIs59minutes() {
    Date firstDate = new Date();
    firstDate.setTime(1577836800000L); // 01/01/2020 12:00:00 AM
    Date secondDate = new Date();
    secondDate.setTime(1577840340000L); // 01/01/2020 11:00:00 PM
    RelativeTimeFormatter rtf = new RelativeTimeFormatter(firstDate, secondDate);
    String output = rtf.getFormattedString();
    assertEquals("59 minutes", output);
  }

  @Test
  public void testRelativeTime_TwoDateObjectsGivenDifferenceIs1hour5minutes_DifferenceStringIs1hour() {
    Date firstDate = new Date();
    firstDate.setTime(1577836800000L); // 01/01/2020 12:00:00 AM
    Date secondDate = new Date();
    secondDate.setTime(1577840700000L); // 01/01/2020 01:05:00 AM
    RelativeTimeFormatter rtf = new RelativeTimeFormatter(firstDate, secondDate);
    String output = rtf.getFormattedString();
    assertEquals("1 hour", output);
  }

  @Test
  public void testRelativeTime_TwoDateObjectsGivenDifferenceIs1hour35minutes_DifferenceStringIs2hours() {
    Date firstDate = new Date();
    firstDate.setTime(1577836800000L); // 01/01/2020 12:00:00 AM
    Date secondDate = new Date();
    secondDate.setTime(1577842500000L); // 01/01/2020 01:35:00 AM
    RelativeTimeFormatter rtf = new RelativeTimeFormatter(firstDate, secondDate);
    String output = rtf.getFormattedString();
    assertEquals("2 hours", output);
  }

  @Test
  public void testRelativeTime_TwoDateObjectsGivenDifferenceIs23hours_DifferenceStringIs23hours() {
    Date firstDate = new Date();
    firstDate.setTime(1577836800000L); // 01/01/2020 12:00:00 AM
    Date secondDate = new Date();
    secondDate.setTime(1577919600000L); // 01/01/2020 11:00:00 PM
    RelativeTimeFormatter rtf = new RelativeTimeFormatter(firstDate, secondDate);
    String output = rtf.getFormattedString();
    assertEquals("23 hours", output);
  }

  @Test
  public void testRelativeTime_TwoDateObjectsGivenDifferenceIs25hours_DifferenceStringIs1day() {
    Date firstDate = new Date();
    firstDate.setTime(1577836800000L); // 01/01/2020 12:00:00 AM
    Date secondDate = new Date();
    secondDate.setTime(1577923200000L); // 01/01/2020 11:00:00 PM
    RelativeTimeFormatter rtf = new RelativeTimeFormatter(firstDate, secondDate);
    String output = rtf.getFormattedString();
    assertEquals("1 day", output);
  }

  @Test
  public void testRelativeTime_TwoDateObjectsGivenDifferenceIs2days_DifferenceStringIs2days() {
    Date firstDate = new Date();
    firstDate.setTime(1577836800000L); // 01/01/2020 12:00:00 AM
    Date secondDate = new Date();
    secondDate.setTime(1578009600000L); // 01/03/2020 12:00:00 AM
    RelativeTimeFormatter rtf = new RelativeTimeFormatter(firstDate, secondDate);
    String output = rtf.getFormattedString();
    assertEquals("2 days", output);
  }

  @Test
  public void testRelativeTime_TwoDateObjectsGivenDifferenceIs2daysButOrderIsReversed_DifferenceStringIs2days() {
    Date firstDate = new Date();
    Date secondDate = new Date();
    firstDate.setTime(1578009600000L); // 01/03/2020 12:00:00 AM
    secondDate.setTime(1577836800000L); // 01/01/2020 12:00:00 AM
    RelativeTimeFormatter rtf = new RelativeTimeFormatter(firstDate, secondDate);
    String output = rtf.getFormattedString();
    assertEquals("2 days", output);
  }
}
