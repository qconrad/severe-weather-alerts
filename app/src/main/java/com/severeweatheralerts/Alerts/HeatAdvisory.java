package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class HeatAdvisory extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.heat;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#FF7F50");
  }
}
