package com.severeweatheralerts.Graphics.GridData;

import java.util.ArrayList;
import java.util.Date;

public class NextMapTimeFromDate {
  private final ArrayList<MapTime> mapTimes;
  private final Date date;

  public NextMapTimeFromDate(ArrayList<MapTime> mapTimes, Date date) {
    this.mapTimes = mapTimes;
    this.date = date;
  }

  public MapTime getMapTime() {
    if (mapTimes.size() < 1) return null;
    for (MapTime mapTime : mapTimes)
      if (mapTime.getDate().equals(date) || mapTime.getDate().after(date)) return mapTime;
    return lastTime();
  }


  private MapTime lastTime() {
    return mapTimes.get(mapTimes.size() - 1);
  }
}
