package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;

import java.util.ArrayList;

// Want to keep minimum android version old so not using java's filtering
public class ReferenceFilter {
  public static ArrayList<Alert> removeReferences(ArrayList<Alert> alerts) {
    ArrayList<Alert> output = getCopy(alerts);
    for (int i = 0; i < output.size(); i++)
      if (shouldFilterAlert(output.get(i))) output.remove(i--);
    return output;
  }

  private static ArrayList<Alert> getCopy(ArrayList<Alert> alerts) {
    return (ArrayList<Alert>) alerts.clone();
  }

  protected static boolean shouldFilterAlert(Alert alert) {
    return alert.isReplaced();
  }
}
