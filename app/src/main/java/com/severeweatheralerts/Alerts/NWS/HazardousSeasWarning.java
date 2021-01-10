package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class HazardousSeasWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.marine;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#D8BFD8");
  }
}
