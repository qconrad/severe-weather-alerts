package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class SpecialMarineWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.marine;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#FFA500");
  }
}
