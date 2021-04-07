package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.GridData.MapTime;
import com.severeweatheralerts.Graphics.GridData.NextMapTimeFromDate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class NextMapTimeFromDateTests {
  @Test
  public void emptyList_NullMapTime() {
    NextMapTimeFromDate nearestMapTimeFromDate = new NextMapTimeFromDate(new ArrayList<>(), new Date(0));
    assertNull(nearestMapTimeFromDate.getMapTime());
  }

  @Test
  public void oneMapTimeReturnsThatMapTime() {
    ArrayList<MapTime> mapTimes = new ArrayList<>();
    String time = "2021-01-25T00:00";
    mapTimes.add(new MapTime(new Date(0L), time));
    NextMapTimeFromDate nearestMapTimeFromDate = new NextMapTimeFromDate(mapTimes, new Date(0));
    assertEquals(time, nearestMapTimeFromDate.getMapTime().getString());
  }

  @Test
  public void TwoTimes_OneAfterDate() {
    ArrayList<MapTime> mapTimes = new ArrayList<>();
    String time = "2021-01-20T00:00";
    mapTimes.add(new MapTime(new Date(0L), time));
    String time2 = "2021-01-28T00:00";
    mapTimes.add(new MapTime(new Date(3L), time2));
    NextMapTimeFromDate nearestMapTimeFromDate = new NextMapTimeFromDate(mapTimes, new Date(2L));
    assertEquals(time2, nearestMapTimeFromDate.getMapTime().getString());
  }

  @Test
  public void ThreeTimes_AfterSecondDate() {
    ArrayList<MapTime> mapTimes = new ArrayList<>();
    String time = "2021-01-20T00:00";
    mapTimes.add(new MapTime(new Date(0L), time));
    String time2 = "2021-01-21T00:00";
    mapTimes.add(new MapTime(new Date(3L), time2));
    String time3 = "2021-01-28T00:00";
    mapTimes.add(new MapTime(new Date(6L), time3));
    NextMapTimeFromDate nearestMapTimeFromDate = new NextMapTimeFromDate(mapTimes, new Date(5L));
    assertEquals(time3, nearestMapTimeFromDate.getMapTime().getString());
  }

  @Test
  public void DateIsEqualToMapTime_ThatTimeReturn() {
    ArrayList<MapTime> mapTimes = new ArrayList<>();
    String time = "2021-01-28T07:00";
    mapTimes.add(new MapTime(new Date(1611817200000L), time));
    String time2 = "2021-01-29T00:00";
    mapTimes.add(new MapTime(new Date(1611817200006L), time2));
    NextMapTimeFromDate nearestMapTimeFromDate = new NextMapTimeFromDate(mapTimes, new Date(1611817200000L));
    assertEquals(time, nearestMapTimeFromDate.getMapTime().getString());
  }
}
