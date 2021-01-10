package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class LocalAreaEmergency extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.hazard;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#C0C0C0");
  }
}
