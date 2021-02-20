package com.severeweatheralerts.Status;

import com.severeweatheralerts.Alerts.Alert;

import java.util.ArrayList;
import java.util.Collections;

public class SubtextGenerator {
  private final ArrayList<Alert> alerts;
  private final ArrayList<String> strings = new ArrayList<>();

  public SubtextGenerator(ArrayList<Alert> alerts) {
    this.alerts = alerts;
  }

  public ArrayList<String> getStrings() {
    for (int i = 0; i < alerts.size(); i++) {
      if (alerts.get(i).getInstruction() != null) {
        String[] split = alerts.get(i).getInstruction().split("(\\.)([^a-z]|$)");
        Collections.addAll(strings, split);
      }
    }
    return strings;
  }
}
