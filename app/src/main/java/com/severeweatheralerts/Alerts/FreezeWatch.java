package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class FreezeWatch extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.freeze;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#00cccc");
  }
}
