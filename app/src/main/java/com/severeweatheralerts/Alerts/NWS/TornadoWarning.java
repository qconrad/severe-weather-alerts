package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class TornadoWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.tornado;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#FF0000");
  }
}
