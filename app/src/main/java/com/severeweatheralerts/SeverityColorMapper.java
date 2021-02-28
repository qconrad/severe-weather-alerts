package com.severeweatheralerts;

import android.graphics.Color;

public class SeverityColorMapper {
  private final int severityIndex;

  public SeverityColorMapper(int severityIndex) {
    this.severityIndex = severityIndex;
  }

  public int getColor() {
    if (severityIndex >= 19) return purple();
    else if (severityIndex >= 17) return darkRed();
    else if (severityIndex >= 15) return red();
    else if (severityIndex >= 13) return orange();
    else if (severityIndex >= 9) return yellow();
    return blue();
  }

  private int blue() {
    return Color.parseColor("#215ca5");
  }

  private int yellow() {
    return Color.parseColor("#fdc500");
  }

  private int orange() {
    return Color.parseColor("#fd8c00");
  }

  private int red() {
    return Color.parseColor("#dc0000");
  }

  private int darkRed() {
    return Color.parseColor("#780000");
  }

  private int purple() {
    return Color.parseColor("#b01994");
  }
}
