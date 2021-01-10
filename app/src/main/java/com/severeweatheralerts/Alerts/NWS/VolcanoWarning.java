package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class VolcanoWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.volcano;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#2F4F4F");
  }
}
