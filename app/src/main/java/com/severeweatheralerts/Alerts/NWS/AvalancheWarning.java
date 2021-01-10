package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
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
