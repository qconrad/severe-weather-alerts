package com.severeweatheralerts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RadarMapTimesParserTests {
  @Test
  public void getTimes_CountIsCorrect() {
    String times = "[1617828000,1617828600,1617829200,1617829800,1617830400,1617831000,1617831600,1617832200,1617832800,1617833400,1617834000,1617834600,1617835200]";
    RadarMapTimeParser mapTimeParser = new RadarMapTimeParser(times);
    assertEquals(13, mapTimeParser.getMapTimes().size());
  }

  @Test
  public void getTimes_FirstStringCorrect() {
    String times = "[1617828000,1617828600,1617829200,1617829800,1617830400,1617831000,1617831600,1617832200,1617832800,1617833400,1617834000,1617834600,1617835200]";
    RadarMapTimeParser mapTimeParser = new RadarMapTimeParser(times);
    assertEquals("1617828000", mapTimeParser.getMapTimes().get(0).getString());
  }

  @Test
  public void getTimes_firstDateCorrect() {
    String times = "[1617828000,1617828600,1617829200,1617829800,1617830400,1617831000,1617831600,1617832200,1617832800,1617833400,1617834000,1617834600,1617835200]";
    RadarMapTimeParser mapTimeParser = new RadarMapTimeParser(times);
    assertEquals(1617828000000L, mapTimeParser.getMapTimes().get(0).getDate().getTime());
  }

  @Test
  public void getTimes_secondDateCorrect() {
    String times = "[1617828000,1617828600,1617829200,1617829800,1617830400,1617831000,1617831600,1617832200,1617832800,1617833400,1617834000,1617834600,1617835200]";
    RadarMapTimeParser mapTimeParser = new RadarMapTimeParser(times);
    assertEquals(1617828600000L, mapTimeParser.getMapTimes().get(1).getDate().getTime());
  }
}
