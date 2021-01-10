package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class WinterStormWatch extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.snow;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#4682B4");
  }
}
