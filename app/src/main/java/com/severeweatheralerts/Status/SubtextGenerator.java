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
    for (int i = 0; i < alerts.size(); i++) getSubtextsFromAlert(alerts.get(i));
    return strings;
  }

  private void getSubtextsFromAlert(Alert alert) {
    if (alert.getLargeHeadline() != null) addString(alert.getLargeHeadline());
    if (alert.getInstruction() != null) addAllSentences(alert);
  }

  private void addString(String string) {
    strings.add(string);
  }

  private void addAllSentences(Alert alert) {
    String[] split = getSentences(alert.getInstruction());
    Collections.addAll(strings, split);
  }

  private String[] getSentences(String text) {
    return text.split("(\\.)([^a-zA-Z]|$)\n*");
  }
}
