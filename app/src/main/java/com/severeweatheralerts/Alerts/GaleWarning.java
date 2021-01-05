package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
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
