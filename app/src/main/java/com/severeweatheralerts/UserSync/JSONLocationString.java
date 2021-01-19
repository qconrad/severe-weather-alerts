package com.severeweatheralerts.UserSync;

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
    locArr.append(getArrayString(locationList.get(i)));
    if (shouldAddComma(i)) locArr.append(",");
  }

  private String getArrayString(Location loc) {
    return "[" + getLat(loc) + "," + getLong(loc) + "]";
  }

  private double getLat(Location loc) {
    return new DecimalTrimmer(loc.getLatitude()).trim(getTrimCount());
  }

  private double getLong(Location loc) {
    return new DecimalTrimmer(loc.getLongitude()).trim(getTrimCount());
  }

  protected int getTrimCount() {
    return 3;
  }

  private boolean shouldAddComma(int i) {
    return i < locationList.size() - 1;
  }

  private String emptyLocationArray() {
    return "[[]]";
  }

  private boolean listProvided() {
    return locationList.size() > 0;
  }
}
