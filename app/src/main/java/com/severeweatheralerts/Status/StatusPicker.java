package com.severeweatheralerts.Status;

import com.severeweatheralerts.Alerts.Alert;

import java.util.ArrayList;

public class StatusPicker {
  private final ArrayList<Alert> inactive;
  private final ArrayList<Alert> active;

  public StatusPicker(ArrayList<Alert> active, ArrayList<Alert> inactive) {
    this.active = active;
    this.inactive = inactive;
  }

  public Status getStatus() {
    if (active.size() > 0) return new ActiveAlerts(
            active.size(),
            active.get(0).getIcon(),
            new SubtextGenerator(active).getStrings());
    if (inactive.size() > 0) return new ClearWithRecent();
    return new Clear();
  }
}
