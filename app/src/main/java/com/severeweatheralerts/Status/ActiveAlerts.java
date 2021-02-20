package com.severeweatheralerts.Status;

import android.graphics.Color;

import com.severeweatheralerts.R;
import com.severeweatheralerts.TextUtils.Plurality;

public class ActiveAlerts implements Status {
  private final int activeCount;
  private final String subText;

  public ActiveAlerts(int activeCount, String subText) {
    this.activeCount = activeCount;
    this.subText = subText;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#FF0000");
  }

  @Override
  public int getIcon() {
    return R.drawable.hazard;
  }

  @Override
  public String getHeadline() {
    return activeCount + " Active " + new Plurality((double)activeCount, "Alert", "Alerts").getText();
  }

  @Override
  public String getSubtext() {
    return subText;
  }
}
