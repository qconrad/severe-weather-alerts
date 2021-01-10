package com.severeweatheralerts.Alerts.NWS;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

public class ShelterInPlaceWarning extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.megaphone;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#CC695E");
  }
}
