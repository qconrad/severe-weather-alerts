package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class NuclearPowerPlantWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.hazard;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#4B0082");
  }
}
