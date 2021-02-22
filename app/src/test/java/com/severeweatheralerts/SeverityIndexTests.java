package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.Alerts.SeverityIndex;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SeverityIndexTests {
  @Test
  public void allUnknown_IndexZero() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setSeverity(Alert.Severity.UNKNOWN);
    defaultAlert.setUrgency(Alert.Urgency.UNKNOWN);
    defaultAlert.setCertainty(Alert.Certainty.UNKNOWN);
    SeverityIndex severityIndex = new SeverityIndex(defaultAlert);
    assertEquals(0, severityIndex.get());
  }

  @Test
  public void UrgencyPast_IndexOne() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setSeverity(Alert.Severity.UNKNOWN);
    defaultAlert.setUrgency(Alert.Urgency.PAST);
    defaultAlert.setCertainty(Alert.Certainty.UNKNOWN);
    SeverityIndex severityIndex = new SeverityIndex(defaultAlert);
    assertEquals(1, severityIndex.get());
  }

  @Test
  public void CertaintyUnlikely_UrgencyPast_IndexTwo() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setSeverity(Alert.Severity.UNKNOWN);
    defaultAlert.setUrgency(Alert.Urgency.PAST);
    defaultAlert.setCertainty(Alert.Certainty.UNLIKELY);
    SeverityIndex severityIndex = new SeverityIndex(defaultAlert);
    assertEquals(2, severityIndex.get());
  }

  @Test
  public void Severity_MinorCertaintyUnlikely_UrgencyPast_IndexFive() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setSeverity(Alert.Severity.MINOR);
    defaultAlert.setUrgency(Alert.Urgency.PAST);
    defaultAlert.setCertainty(Alert.Certainty.UNLIKELY);
    SeverityIndex severityIndex = new SeverityIndex(defaultAlert);
    assertEquals(5, severityIndex.get());
  }
}
