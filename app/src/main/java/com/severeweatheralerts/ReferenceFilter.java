package com.severeweatheralerts;

import java.util.ArrayList;

// Want to keep minimum android version old so not using java's filtering
public class ReferenceFilter {
  public static ArrayList<Alert> removeReferences(ArrayList<Alert> alerts) {
    for (int i = 0; i < alerts.size(); i++)
      if (shouldFilterAlert(alerts.get(i))) alerts.remove(i--);
    return alerts;
  }

  protected static boolean shouldFilterAlert(Alert alert) {
    return alert.isReplaced();
  }
}
