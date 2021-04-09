package com.severeweatheralerts.UserSync;

import com.severeweatheralerts.Adapters.GCSCoordinate;

import java.util.ArrayList;

public class JSONLocationString {
  private final ArrayList<GCSCoordinate> locationList;

  public JSONLocationString(ArrayList<GCSCoordinate> locationList) {
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

  private String getArrayString(GCSCoordinate loc) {
    return "[" + getLong(loc) + "," + getLat(loc) + "]";
  }

  private double getLat(GCSCoordinate loc) {
    return new DecimalTrimmer(loc.getLat()).trim(getTrimCount());
  }

  private double getLong(GCSCoordinate loc) {
    return new DecimalTrimmer(loc.getLong()).trim(getTrimCount());
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
