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
    if (active.size() > 0) {
      if (active.get(0).getInstruction() != null) {
        String[] split = active.get(0).getInstruction().split("\\.");
        return new ActiveAlerts(active.size(), split[0]);
      }
      else {
        return new ActiveAlerts(active.size(), "Take action");
      }
    }
    if (inactive.size() > 0)
      return new ClearWithRecent();
    return new Clear();
  }
}
