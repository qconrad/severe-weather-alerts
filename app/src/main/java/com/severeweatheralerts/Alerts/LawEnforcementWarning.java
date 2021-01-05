package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class LawEnforcementWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.megaphone;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#66cc00");
  }
}
