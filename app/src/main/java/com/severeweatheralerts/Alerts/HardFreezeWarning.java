package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class HardFreezeWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.freeze;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#9400d3");
  }
}
