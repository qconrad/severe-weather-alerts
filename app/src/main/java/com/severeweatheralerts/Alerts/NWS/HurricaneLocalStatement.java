package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class HurricaneLocalStatement extends Alert {
  @Override
  public String getName() {
    return "Hurricane Local Statement";
  }

  @Override
  public int getIcon() {
    return R.drawable.hurricane;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#CCB391");
  }
}
