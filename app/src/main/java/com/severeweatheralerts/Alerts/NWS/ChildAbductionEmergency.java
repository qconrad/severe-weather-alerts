package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class ChildAbductionEmergency extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.missing;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#ccad00");
  }
}
