package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class AirQualityAlert extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.airquality;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#808080");
  }
}
