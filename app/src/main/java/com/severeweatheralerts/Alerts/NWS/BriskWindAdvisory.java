package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class BriskWindAdvisory extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.wind;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#D8BFD8");
  }
}
