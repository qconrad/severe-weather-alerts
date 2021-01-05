package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
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
