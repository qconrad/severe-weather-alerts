package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class FreezeWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.freeze;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#483D8B");
  }
}
