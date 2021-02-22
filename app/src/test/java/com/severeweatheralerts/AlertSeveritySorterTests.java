package com.severeweatheralerts;

import com.severeweatheralerts.AlertListTools.SeveritySorter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class AlertSeveritySorterTests {
  @Test
  public void orderCorrect_Untouched() {
    ArrayList<Alert> alerts = new ArrayList<>();
    DefaultAlert da1 = new DefaultAlert();
    da1.setCertainty(Alert.Certainty.UNKNOWN);
    da1.setUrgency(Alert.Urgency.UNKNOWN);
    da1.setSeverity(Alert.Severity.MODERATE);
    DefaultAlert da2 = new DefaultAlert();
    da2.setCertainty(Alert.Certainty.UNKNOWN);
    da2.setUrgency(Alert.Urgency.UNKNOWN);
    da2.setSeverity(Alert.Severity.UNKNOWN);
    alerts.add(da1);
    alerts.add(da2);
    SeveritySorter severitySorter = new SeveritySorter(alerts);
    assertEquals(Alert.Severity.MODERATE, severitySorter.getSorted().get(0).getSeverity());
  }

  @Test
  public void orderNotCorrect_Sorted() {
    ArrayList<Alert> alerts = new ArrayList<>();
    DefaultAlert da1 = new DefaultAlert();
    da1.setCertainty(Alert.Certainty.UNKNOWN);
    da1.setUrgency(Alert.Urgency.UNKNOWN);
    da1.setSeverity(Alert.Severity.UNKNOWN);
    DefaultAlert da2 = new DefaultAlert();
    da2.setCertainty(Alert.Certainty.UNKNOWN);
    da2.setUrgency(Alert.Urgency.UNKNOWN);
    da2.setSeverity(Alert.Severity.MODERATE);
    alerts.add(da1);
    alerts.add(da2);
    SeveritySorter severitySorter = new SeveritySorter(alerts);
    assertEquals(Alert.Severity.MODERATE, severitySorter.getSorted().get(0).getSeverity());
  }

  @Test
  public void orderNotCorrect_OriginalListNotTouched() {
    ArrayList<Alert> alerts = new ArrayList<>();
    DefaultAlert da1 = new DefaultAlert();
    da1.setCertainty(Alert.Certainty.UNKNOWN);
    da1.setUrgency(Alert.Urgency.UNKNOWN);
    da1.setSeverity(Alert.Severity.UNKNOWN);
    DefaultAlert da2 = new DefaultAlert();
    da2.setCertainty(Alert.Certainty.UNKNOWN);
    da2.setUrgency(Alert.Urgency.UNKNOWN);
    da2.setSeverity(Alert.Severity.MODERATE);
    alerts.add(da1);
    alerts.add(da2);
    new SeveritySorter(alerts).getSorted();
    assertEquals(Alert.Severity.UNKNOWN, alerts.get(0).getSeverity());
  }
}
