package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

public class LakeEffectSnowAdvisory extends Alert {
  @Override
  public int getIcon() {
    return R.drawable.snow;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#48D1CC");
  }
}
