package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class LowWaterAdvisory extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.hazard;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#A52A2A");
  }
}
