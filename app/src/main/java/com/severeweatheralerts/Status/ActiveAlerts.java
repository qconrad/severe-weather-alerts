package com.severeweatheralerts.Status;

import android.graphics.Color;

import com.severeweatheralerts.TextUtils.Plurality;

import java.util.ArrayList;

public class ActiveAlerts implements Status {
  private final int activeCount;
  private final ArrayList<String> subTexts;
  private final int icon;

  public ActiveAlerts(int activeCount, int icon, ArrayList<String> subTexts) {
    this.activeCount = activeCount;
    this.subTexts = subTexts;
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
  public ArrayList<String> getSubtexts() {
    return subTexts;
  }
}
