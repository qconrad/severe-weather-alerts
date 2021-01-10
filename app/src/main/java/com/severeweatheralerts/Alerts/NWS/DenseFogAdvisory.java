package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class DenseFogAdvisory extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.fog;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#708090");
  }
}
