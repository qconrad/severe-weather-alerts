package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class AirStagnationAdvisory extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.airquality;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#808080");
  }
}
