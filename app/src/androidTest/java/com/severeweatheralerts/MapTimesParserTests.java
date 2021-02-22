package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.Generators.MapTime;
import com.severeweatheralerts.JSONParsing.MapTimeParser;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class MapTimesParserTests {

  @Test
  public void getTimes_2TimesGiven_2Returned() {
    String times = "[[\"2021-02-15T00:00\",\"2021-02-14T20:00\"],[\"2021-02-15T06:00\",\"2021-02-14T20:00\"]]";
    MapTimeParser mapTimeParser = new MapTimeParser(times);
    assertEquals(2, mapTimeParser.getMapTimes().size());
  }

  @Test
  public void getTimes_3TimesGiven_3Returned() {
    String times = "[[\"2021-02-15T00:00\",\"2021-02-14T20:00\"],[\"2021-02-15T06:00\",\"2021-02-14T20:00\"],[\"2021-02-15T00:00\",\"2021-02-14T20:00\"]]";
    MapTimeParser mapTimeParser = new MapTimeParser(times);
    assertEquals(3, mapTimeParser.getMapTimes().size());
  }

  @Test
  public void getTimes_3TimesGiven_FirstDateNotNull() {
    String times = "[[\"2021-02-15T00:00\",\"2021-02-14T20:00\"],[\"2021-02-15T06:00\",\"2021-02-14T20:00\"],[\"2021-02-15T00:00\",\"2021-02-14T20:00\"]]";
    MapTimeParser mapTimeParser = new MapTimeParser(times);
    ArrayList<MapTime> dates = mapTimeParser.getMapTimes();
    assertNotNull(dates.get(0));
  }

  @Test
  public void getTimes_3TimesGiven_NotTheSame() {
    String times = "[[\"2021-02-15T00:00\",\"2021-02-14T20:00\"],[\"2021-02-15T06:00\",\"2021-02-14T19:00\"],[\"2021-02-15T00:00\",\"2021-02-14T20:00\"]]";
    MapTimeParser mapTimeParser = new MapTimeParser(times);
    ArrayList<MapTime> dates = mapTimeParser.getMapTimes();
    assertNotEquals(dates.get(1).getDate().getTime(), dates.get(0).getDate().getTime());
  }

  @Test
  public void getTimes_3TimesGiven_FirstStringCorrect() {
    String times = "[[\"2021-02-14T20:00\",\"2021-02-14T20:00\"],[\"2021-02-15T06:00\",\"2021-02-14T19:00\"],[\"2021-02-15T00:00\",\"2021-02-14T20:00\"]]";
    MapTimeParser mapTimeParser = new MapTimeParser(times);
    assertEquals("2021-02-14T20:00", mapTimeParser.getMapTimes().get(0).getString());
  }

  @Test
  public void getTimes_3TimesGiven_DifferentFirstStringCorrect() {
    String times = "[[\"2021-02-15T00:00\",\"2021-02-15T20:00\"],[\"2021-02-15T06:00\",\"2021-02-14T19:00\"],[\"2021-02-15T00:00\",\"2021-02-14T20:00\"]]";
    MapTimeParser mapTimeParser = new MapTimeParser(times);
    assertEquals("2021-02-15T00:00", mapTimeParser.getMapTimes().get(0).getString());
  }
}
