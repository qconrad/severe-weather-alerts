package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ReferenceFilterTests {
  @Test
  public void removeReferences_ReplacedAlertProvided_NoItemsInList() {
    ArrayList<Alert> alerts = new ArrayList<>();
    DefaultAlert alert1 = new DefaultAlert();
    alert1.setReplaced(true);
    alerts.add(alert1);
    assertEquals(0, AlertFilter.removeReferences(alerts).size());
  }

  @Test
  public void removeReferences_SecondAlertReplaced_OneAlertInList() {
    ArrayList<Alert> alerts = new ArrayList<>();
    DefaultAlert alert1 = new DefaultAlert();
    DefaultAlert alert2 = new DefaultAlert();
    alert2.setReplaced(true);
    alerts.add(alert1);
    alerts.add(alert2);
    assertEquals(1, AlertFilter.removeReferences(alerts).size());
  }

  @Test
  public void removeReferences_SecondAndThirdReplaced_OneAlertInList() {
    ArrayList<Alert> alerts = new ArrayList<>();
    DefaultAlert alert1 = new DefaultAlert();
    DefaultAlert alert2 = new DefaultAlert();
    DefaultAlert alert3 = new DefaultAlert();
    alert2.setReplaced(true);
    alert3.setReplaced(true);
    alerts.add(alert1);
    alerts.add(alert2);
    alerts.add(alert3);
    assertEquals(1, AlertFilter.removeReferences(alerts).size());
  }

  @Test
  public void removeReferences_SecondAndThirdReplaced_OriginalListNotAffected() {
    ArrayList<Alert> alerts = new ArrayList<>();
    DefaultAlert alert1 = new DefaultAlert();
    DefaultAlert alert2 = new DefaultAlert();
    DefaultAlert alert3 = new DefaultAlert();
    alert2.setReplaced(true);
    alert3.setReplaced(true);
    alerts.add(alert1);
    alerts.add(alert2);
    alerts.add(alert3);
    AlertFilter.removeReferences(alerts);
    assertEquals(3, alerts.size());
  }
}
