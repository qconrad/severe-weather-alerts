package com.severeweatheralerts;

import android.graphics.Color;

public class WinterStormWarning extends Alert {
  @Override
  public int getColor() {
    return Color.parseColor("#FF69B4");
  }
}
