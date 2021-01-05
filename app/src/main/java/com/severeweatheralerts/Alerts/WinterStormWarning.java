package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class WinterStormWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.snow;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#FF69B4");
  }
}
