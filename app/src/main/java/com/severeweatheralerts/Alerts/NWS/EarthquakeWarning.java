package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class EarthquakeWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.earthquake;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#8B4513");
  }
}
