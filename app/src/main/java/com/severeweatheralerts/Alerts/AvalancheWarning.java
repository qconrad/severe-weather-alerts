package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class AvalancheWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.avalanche;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#1E90FF");
  }
}
