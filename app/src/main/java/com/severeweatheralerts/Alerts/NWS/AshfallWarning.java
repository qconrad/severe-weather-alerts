package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class AshfallWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.volcano;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#A9A9A9");
  }
}
