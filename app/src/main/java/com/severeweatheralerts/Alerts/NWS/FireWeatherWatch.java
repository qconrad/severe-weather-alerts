package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class FireWeatherWatch extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.fire;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#E6C89C");
  }
}
