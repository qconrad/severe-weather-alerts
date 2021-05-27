package com.severeweatheralerts.Adapters;

import com.severeweatheralerts.Alerts.MotionVector;
import com.severeweatheralerts.TextUtils.RegExMatcher;

public class MotionDescriptionAdapter {
  private final String motionDescription;

  public MotionDescriptionAdapter(String motionDescription) {
    this.motionDescription = motionDescription;
  }

  public MotionVector getMotionVector() {
    if (motionDescription == null) return null;
    String[] vectorSplit = getVector().split("DEG...");
    return new MotionVector(Integer.parseInt(vectorSplit[0]), Integer.parseInt(vectorSplit[1]));
  }

  private String getVector() {
    return RegExMatcher.match("\\d{1,3}DEG\\.\\.\\.\\d*", motionDescription).get(0);
  }
}
