package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class FreezingRainAdvisory extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.ice;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#da70d6");
  }
}