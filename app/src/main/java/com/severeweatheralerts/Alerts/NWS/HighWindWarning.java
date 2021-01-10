package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class HighWindWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.wind;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#DAA520");
  }
}
