package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class ExcessiveHeatWatch extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.heat;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#800000");
  }
}
