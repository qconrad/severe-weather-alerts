package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class TsunamiWatch extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.wave;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#cc00cc");
  }
}
