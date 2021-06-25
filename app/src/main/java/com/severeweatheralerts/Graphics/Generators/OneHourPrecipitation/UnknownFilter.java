package com.severeweatheralerts.Graphics.Generators.OneHourPrecipitation;

import java.util.ArrayList;

/* Current source fo radar hydrometer classification does not differentiate large hail and unknown
   This class filters out random specs of unknown that is not near heavy rain or hail */
public class UnknownFilter {
  private final ArrayList<Integer> values;

  public UnknownFilter(ArrayList<Integer> values) {
    this.values = values;
  }

  public ArrayList<Integer> filter() {
    for (int i = 0; i < values.size(); i++) filterUnkownNoise(i);
    return values;
  }

  private void filterUnkownNoise(int i) {
    if (unknownOrLargeHail(i)) conditionallyFilter(i);
  }

  private boolean unknownOrLargeHail(int i) {
    return values.get(i) == 5;
  }

  private void conditionallyFilter(int i) {
    if (!nearHeavyRainOrHail() && !allUnknown()) values.set(i, 0);
  }

  private boolean allUnknown() {
    for (Integer value : values) if (value != 5) return false;
    return true;
  }

  private boolean nearHeavyRainOrHail() {
    return values.contains(3) || values.contains(4);
  }
}
