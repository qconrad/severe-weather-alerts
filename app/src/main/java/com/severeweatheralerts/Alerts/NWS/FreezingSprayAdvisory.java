package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class FreezingSprayAdvisory extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.ice;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#0099cc");
  }
}
