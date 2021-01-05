package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class TropicalStormWatch extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.tropicalstorm;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#f08080");
  }
}
