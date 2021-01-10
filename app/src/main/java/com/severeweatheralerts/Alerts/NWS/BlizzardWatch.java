package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class BlizzardWatch extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.blizzard;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#89CC25");
  }
}
