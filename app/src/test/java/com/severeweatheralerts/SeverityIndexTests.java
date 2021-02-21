package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;

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
    assertEquals(0, severityIndex.calculate());
  }

  @Test
  public void UrgencyPast_IndexOne() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setSeverity(Alert.Severity.UNKNOWN);
    defaultAlert.setUrgency(Alert.Urgency.PAST);
    defaultAlert.setCertainty(Alert.Certainty.UNKNOWN);
    SeverityIndex severityIndex = new SeverityIndex(defaultAlert);
    assertEquals(1, severityIndex.calculate());
  }

  @Test
  public void CertaintyUnlikely_UrgencyPast_IndexTwo() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setSeverity(Alert.Severity.UNKNOWN);
    defaultAlert.setUrgency(Alert.Urgency.PAST);
    defaultAlert.setCertainty(Alert.Certainty.UNLIKELY);
    SeverityIndex severityIndex = new SeverityIndex(defaultAlert);
    assertEquals(2, severityIndex.calculate());
  }

  @Test
  public void Severity_MinorCertaintyUnlikely_UrgencyPast_IndexFive() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setSeverity(Alert.Severity.MINOR);
    defaultAlert.setUrgency(Alert.Urgency.PAST);
    defaultAlert.setCertainty(Alert.Certainty.UNLIKELY);
    SeverityIndex severityIndex = new SeverityIndex(defaultAlert);
    assertEquals(5, severityIndex.calculate());
  }
}
