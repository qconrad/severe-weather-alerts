package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;

import java.util.ArrayList;
import java.util.Collections;

public class SeveritySorter {

  private final ArrayList<Alert> alerts;

  public SeveritySorter(ArrayList<Alert> alerts) {
    this.alerts = getCopy(alerts);
  }

  public ArrayList<Alert> getSorted() {
    Collections.sort(alerts, (one, two) -> new SeverityIndex(two).get() - new SeverityIndex(one).get());
    return alerts;
  }

  private ArrayList<Alert> getCopy(ArrayList<Alert> alerts) {
    return (ArrayList<Alert>) alerts.clone();
  }
}
