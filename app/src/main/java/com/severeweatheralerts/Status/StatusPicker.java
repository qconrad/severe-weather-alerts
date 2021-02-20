package com.severeweatheralerts.Status;

import com.severeweatheralerts.Alerts.Alert;

import java.util.ArrayList;

public class StatusPicker {
  private final ArrayList<Alert> inactive;

  public StatusPicker(ArrayList<Alert> active, ArrayList<Alert> inactive) {
    this.inactive = inactive;
  }

  public Status getStatus() {
    if (inactive.size() > 0)
      return new ClearWithRecent();
    return new Clear();
  }
}
