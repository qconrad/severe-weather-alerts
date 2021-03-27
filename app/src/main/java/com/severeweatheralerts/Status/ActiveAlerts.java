package com.severeweatheralerts.Status;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.SeverityIndex;
import com.severeweatheralerts.Alerts.SeverityColorMapper;
import com.severeweatheralerts.TextUtils.Plurality;

import java.util.ArrayList;

public class ActiveAlerts implements Status {
  private final ArrayList<Alert> activeAlerts;

  public ActiveAlerts(ArrayList<Alert> activeAlerts) {
    this.activeAlerts = activeAlerts;
  }

  @Override
  public int getColor() {
    return new SeverityColorMapper(new SeverityIndex(activeAlerts.get(0)).get()).getColor();
  }

  @Override
  public int getIcon() {
    return activeAlerts.get(0).getIcon();
  }

  @Override
  public String getHeadline() {
    return activeAlerts.size() + " Active " + new Plurality((double)activeAlerts.size(), "Alert", "Alerts").getText();
  }

  @Override
  public ArrayList<String> getSubtexts() {
    return new SubtextGenerator(activeAlerts).getStrings();
  }
}
