package com.severeweatheralerts.Status;

import android.graphics.Color;

import com.severeweatheralerts.TextUtils.Plurality;

public class ActiveAlerts implements Status {
  private final int activeCount;
  private final String subText;
  private final int icon;

  public ActiveAlerts(int activeCount, int icon, String subText) {
    this.activeCount = activeCount;
    this.subText = subText;
    this.icon = icon;
  }

  @Override
  public int getColor() {
    return Color.parseColor("#FF0000");
  }

  @Override
  public int getIcon() {
    return icon;
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
