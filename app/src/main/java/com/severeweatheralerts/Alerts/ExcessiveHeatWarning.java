package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class ExcessiveHeatWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.heat;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#C71585");
  }
}
