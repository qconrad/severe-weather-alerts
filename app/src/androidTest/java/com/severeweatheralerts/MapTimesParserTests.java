package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.MapTimeParser;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class MapTimesParserTests {

  @Test
  public void getTimes_2TimesGiven_2Returned() {
    String times = "[[\"2021-02-15T00:00\",\"2021-02-14T20:00\"],[\"2021-02-15T06:00\",\"2021-02-14T20:00\"]]";
    MapTimeParser mapTimeParser = new MapTimeParser(times);
    assertEquals(2, mapTimeParser.getDates().size());
  }

  @Test
  public void getTimes_3TimesGiven_3Returned() {
    String times = "[[\"2021-02-15T00:00\",\"2021-02-14T20:00\"],[\"2021-02-15T06:00\",\"2021-02-14T20:00\"],[\"2021-02-15T00:00\",\"2021-02-14T20:00\"]]";
    MapTimeParser mapTimeParser = new MapTimeParser(times);
    assertEquals(3, mapTimeParser.getDates().size());
  }

  @Test
  public void getTimes_3TimesGiven_FirstDateNotNull() {
    String times = "[[\"2021-02-15T00:00\",\"2021-02-14T20:00\"],[\"2021-02-15T06:00\",\"2021-02-14T20:00\"],[\"2021-02-15T00:00\",\"2021-02-14T20:00\"]]";
    MapTimeParser mapTimeParser = new MapTimeParser(times);
    ArrayList<Date> dates = mapTimeParser.getDates();
    assertNotNull(dates.get(0));
  }

  @Test
  public void getTimes_3TimesGiven_NotTheSame() {
    String times = "[[\"2021-02-15T00:00\",\"2021-02-14T20:00\"],[\"2021-02-15T06:00\",\"2021-02-14T19:00\"],[\"2021-02-15T00:00\",\"2021-02-14T20:00\"]]";
    MapTimeParser mapTimeParser = new MapTimeParser(times);
    ArrayList<Date> dates = mapTimeParser.getDates();
    assertNotEquals(dates.get(1).getTime(), dates.get(0).getTime());
  }
}
