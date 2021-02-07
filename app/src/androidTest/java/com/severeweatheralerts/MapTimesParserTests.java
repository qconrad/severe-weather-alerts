package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.MapTimeParser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MapTimesParserTests {
  @Test
  public void getTimes_2TimesGiven_2Returned() {
    String times = "[[\"2021-02-08T00:00\",\"2021-02-07T21:00\"]]";
    MapTimeParser mapTimeParser = new MapTimeParser(times);
    assertEquals(2, mapTimeParser.getDates().size());
  }

  @Test
  public void getTimes_3TimesGiven_3Returned() {
    String times = "[[\"2021-02-08T00:00\",\"2021-02-07T21:00\",\"2021-02-07T21:00\"]]";
    MapTimeParser mapTimeParser = new MapTimeParser(times);
    assertEquals(3, mapTimeParser.getDates().size());
  }
}
