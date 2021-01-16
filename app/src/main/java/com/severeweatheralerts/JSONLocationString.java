package com.severeweatheralerts;

import com.severeweatheralerts.Location.Location;

import java.util.ArrayList;

public class JSONLocationString {
  private final ArrayList<Location> locationList;

  public JSONLocationString(ArrayList<Location> locationList) {
    this.locationList = locationList;
  }

  public String getString() {
    if (listProvided()) return buildJSONArrayFromList();
    return emptyLocationArray();
  }

  private String buildJSONArrayFromList() {
    StringBuilder locArr = new StringBuilder("[");
    for (int i = 0; i < locationList.size(); i++) addLocation(locArr, i);
    locArr.append("]");
    return locArr.toString();
  }

  private void addLocation(StringBuilder locArr, int i) {
    Location curLoc = locationList.get(i);
    locArr.append("[").append(curLoc.getLatitude()).append(",").append(curLoc.getLongitude()).append("]");
    if (shouldAddComma(i)) locArr.append(",");
  }

  private boolean shouldAddComma(int i) {
    return i <= locationList.size()-2;
  }

  private String emptyLocationArray() {
    return "[[]]";
  }

  private boolean listProvided() {
    return locationList.size() > 0;
  }
}
