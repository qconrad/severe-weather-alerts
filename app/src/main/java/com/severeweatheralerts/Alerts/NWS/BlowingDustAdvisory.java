package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class BlowingDustAdvisory extends Alert {
  public int getIcon() { return R.drawable.wind; }

  public int getColor() {
    return Color.parseColor("#BDB76B");
  }
}
