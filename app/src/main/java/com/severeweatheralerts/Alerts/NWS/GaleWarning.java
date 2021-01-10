package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class GaleWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.gale;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#DDA0DD");
  }
}
