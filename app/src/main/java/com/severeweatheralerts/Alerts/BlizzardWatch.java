package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class BlizzardWatch extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.blizzard;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#89CC25");
  }
}
