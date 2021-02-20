package com.severeweatheralerts.Status;

import com.severeweatheralerts.Alerts.Alert;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StatusPicker {
  private final ArrayList<Alert> inactive;
  private final ArrayList<Alert> active;

  public StatusPicker(ArrayList<Alert> active, ArrayList<Alert> inactive) {
    this.active = active;
    this.inactive = inactive;
  }

  public Status getStatus() {
    if (active.size() > 0) {
      ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < active.size(); i++) {
          if (active.get(i).getInstruction() != null) {
          String[] split = active.get(i).getInstruction().split("\\.");
          for (String sentence : split) {
            strings.add(sentence.trim());
          }
        }
      }
      return new ActiveAlerts(active.size(), active.get(0).getIcon(), strings);
    }
    if (inactive.size() > 0)
      return new ClearWithRecent();
    return new Clear();
  }
}
