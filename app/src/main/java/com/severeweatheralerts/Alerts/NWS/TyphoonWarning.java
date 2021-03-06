package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class TyphoonWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.hurricane;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#DC143C");
  }
}
