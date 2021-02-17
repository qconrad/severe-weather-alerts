package com.severeweatheralerts.Graphics.Polygon;

import java.util.ArrayList;

public class Polygon {
  private final ArrayList<MercatorCoordinate> coordList = new ArrayList<>();

  public void addCoordinate(MercatorCoordinate mercatorCoordinate) {
    coordList.add(mercatorCoordinate);
  }

  public int getCoordinateCount() {
    return coordList.size();
  }

  public MercatorCoordinate getCoordinate(int index) {
    return coordList.get(index);
  }

  public ArrayList<MercatorCoordinate> getCoordinates() {
    return coordList;
  }
}
