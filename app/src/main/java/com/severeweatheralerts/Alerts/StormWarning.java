package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class StormWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.tropicalstorm;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#9400D3");
  }
}
