package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class RedFlagWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.fire;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#FF1493");
  }
}
