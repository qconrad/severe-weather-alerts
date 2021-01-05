package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class FreezingFogAdvisory extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.fog;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#567fcc");
  }
}
