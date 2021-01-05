package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class FlashFloodWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.flood;
  }

  @Override public int getColor() {
    return Color.parseColor("#8B0000");
  }
}
