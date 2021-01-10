package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class TelephoneOutage extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.phone;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#C0C0C0");
  }
}
