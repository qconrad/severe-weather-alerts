package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class AvalancheWatch extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.avalanche;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#F4A460");
  }
}
