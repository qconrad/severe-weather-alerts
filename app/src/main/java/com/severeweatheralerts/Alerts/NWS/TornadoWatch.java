package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class TornadoWatch extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.tornado;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#cccc00");
  }
}
