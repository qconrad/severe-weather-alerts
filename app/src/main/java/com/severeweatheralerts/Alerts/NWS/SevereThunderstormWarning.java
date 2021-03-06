package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class SevereThunderstormWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.thunderstorm;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#FFA500");
  }
}
