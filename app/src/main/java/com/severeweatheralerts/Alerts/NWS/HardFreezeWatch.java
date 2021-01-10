package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class HardFreezeWatch extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.freeze;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#4169e1");
  }
}
