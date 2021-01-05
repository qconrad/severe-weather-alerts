package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class SnowSquallWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.blizzard;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#C71585");
  }
}
