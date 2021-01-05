package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class SevereThunderstormWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.thunderstorm;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#FFA500");
  }
}
