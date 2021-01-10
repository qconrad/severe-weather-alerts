package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class SnowSquallWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.blizzard;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#C71585");
  }
}
