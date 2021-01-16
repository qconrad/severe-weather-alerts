package com.severeweatheralerts;

import com.severeweatheralerts.Adapters.ReferenceLinker;
import com.severeweatheralerts.Adapters.UnadaptedAlert;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.Alerts.NWS.WinterStormWarning;
import com.severeweatheralerts.Alerts.NWS.WinterStormWatch;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ReferenceLinkerTests {
  @Test
  public void linkReferences_OneReferenceIsLinked_ReturnsCorrectID() {
    UnadaptedAlert alert = new UnadaptedAlert();
    alert.addReferenceId("testId1");
    UnadaptedAlert reference = new UnadaptedAlert();
    reference.setId("testId1");
    ArrayList<UnadaptedAlert> unAdaptedAlerts = new ArrayList<>();
    unAdaptedAlerts.add(alert);
    unAdaptedAlerts.add(reference);
    ArrayList<Alert> adaptedAlerts = new ArrayList<>();
    DefaultAlert adaptedAlert = new DefaultAlert();
    DefaultAlert adaptedReference = new DefaultAlert();
    adaptedReference.setNwsId("testId1");
    adaptedAlerts.add(adaptedAlert);
    adaptedAlerts.add(adaptedReference);
    ArrayList<Alert> linked = new ReferenceLinker(unAdaptedAlerts, adaptedAlerts).linkReferences();
    assertEquals("testId1", linked.get(0).getReference(0).getNwsId());
  }

  @Test
  public void linkReferences_TwoReferencesIsLinked_SecondHasCorrectID() {
    UnadaptedAlert alert1 = new UnadaptedAlert();
    UnadaptedAlert alert2 = new UnadaptedAlert();
    UnadaptedAlert reference = new UnadaptedAlert();
    reference.setId("testId1");
    alert2.addReferenceId("testId1");
    ArrayList<UnadaptedAlert> unAdaptedAlerts = new ArrayList<>();
    unAdaptedAlerts.add(alert1);
    unAdaptedAlerts.add(alert2);
    unAdaptedAlerts.add(reference);
    ArrayList<Alert> adaptedAlerts = new ArrayList<>();
    DefaultAlert adaptedAlert1 = new DefaultAlert();
    DefaultAlert adaptedAlert2 = new DefaultAlert();
    DefaultAlert adaptedReference = new DefaultAlert();
    adaptedReference.setNwsId("testId1");
    adaptedAlerts.add(adaptedAlert1);
    adaptedAlerts.add(adaptedAlert2);
    adaptedAlerts.add(adaptedReference);
    ArrayList<Alert> linked = new ReferenceLinker(unAdaptedAlerts, adaptedAlerts).linkReferences();
    assertEquals("testId1", linked.get(1).getReference(0).getNwsId());
  }

  @Test
  public void linkReferences_OneAlertWithTwoReferencesAdded_ReferencesDoNotHaveNestedReferences() {
    UnadaptedAlert alert = new UnadaptedAlert();
    UnadaptedAlert reference1 = new UnadaptedAlert();
    UnadaptedAlert reference2 = new UnadaptedAlert();
    reference1.setId("reference1");
    reference2.setId("reference2");
    alert.addReferenceId("reference1");
    alert.addReferenceId("reference2");
    reference1.addReferenceId("reference2");
    ArrayList<UnadaptedAlert> unAdaptedAlerts = new ArrayList<>();
    unAdaptedAlerts.add(alert);
    unAdaptedAlerts.add(reference1);
    unAdaptedAlerts.add(reference2);
    ArrayList<Alert> adaptedAlerts = new ArrayList<>();
    DefaultAlert adaptedAlert = new DefaultAlert();
    DefaultAlert adaptedReference1 = new DefaultAlert();
    DefaultAlert adaptedReference2 = new DefaultAlert();
    adaptedReference1.setNwsId("reference1");
    adaptedReference2.setNwsId("reference2");
    adaptedAlerts.add(adaptedAlert);
    adaptedAlerts.add(adaptedReference1);
    adaptedAlerts.add(adaptedReference2);
    ArrayList<Alert> linked = new ReferenceLinker(unAdaptedAlerts, adaptedAlerts).linkReferences();
    assertEquals(0, linked.get(1).getReferenceCount());
  }

  @Test
  public void linkReferences_InvalidIdProvided_NotIncluded() {
    UnadaptedAlert alert = new UnadaptedAlert();
    UnadaptedAlert reference1 = new UnadaptedAlert();
    UnadaptedAlert reference2 = new UnadaptedAlert();
    reference1.setId("reference1");
    reference2.setId("fakeid");
    alert.addReferenceId("reference1");
    alert.addReferenceId("reference2");
    reference1.addReferenceId("reference2");
    ArrayList<UnadaptedAlert> unAdaptedAlerts = new ArrayList<>();
    unAdaptedAlerts.add(alert);
    unAdaptedAlerts.add(reference1);
    unAdaptedAlerts.add(reference2);
    ArrayList<Alert> adaptedAlerts = new ArrayList<>();
    DefaultAlert adaptedAlert = new DefaultAlert();
    DefaultAlert adaptedReference1 = new DefaultAlert();
    DefaultAlert adaptedReference2 = new DefaultAlert();
    adaptedReference1.setNwsId("reference1");
    adaptedReference2.setNwsId("fakeid");
    adaptedAlerts.add(adaptedAlert);
    adaptedAlerts.add(adaptedReference1);
    adaptedAlerts.add(adaptedReference2);
    ArrayList<Alert> linked = new ReferenceLinker(unAdaptedAlerts, adaptedAlerts).linkReferences();
    assertEquals(0, linked.get(1).getReferenceCount());
  }

  @Test
  public void linkReferences_OneReferenceIsLinked_ReferenceShowsReplaced() {
    UnadaptedAlert alert = new UnadaptedAlert();
    alert.addReferenceId("testId1");
    UnadaptedAlert reference = new UnadaptedAlert();
    reference.setId("testId1");
    ArrayList<UnadaptedAlert> unAdaptedAlerts = new ArrayList<>();
    unAdaptedAlerts.add(alert);
    unAdaptedAlerts.add(reference);
    ArrayList<Alert> adaptedAlerts = new ArrayList<>();
    DefaultAlert adaptedAlert = new DefaultAlert();
    DefaultAlert adaptedReference = new DefaultAlert();
    adaptedReference.setNwsId("testId1");
    adaptedAlerts.add(adaptedAlert);
    adaptedAlerts.add(adaptedReference);
    ArrayList<Alert> linked = new ReferenceLinker(unAdaptedAlerts, adaptedAlerts).linkReferences();
    assertTrue(linked.get(0).getReference(0).isReplaced());
  }

  @Test
  public void linkReferences_OneReferenceIsLinked_RootAlertNotReplaced() {
    UnadaptedAlert alert = new UnadaptedAlert();
    alert.addReferenceId("testId1");
    UnadaptedAlert reference = new UnadaptedAlert();
    reference.setId("testId1");
    ArrayList<UnadaptedAlert> unAdaptedAlerts = new ArrayList<>();
    unAdaptedAlerts.add(alert);
    unAdaptedAlerts.add(reference);
    ArrayList<Alert> adaptedAlerts = new ArrayList<>();
    DefaultAlert adaptedAlert = new DefaultAlert();
    DefaultAlert adaptedReference = new DefaultAlert();
    adaptedReference.setNwsId("testId1");
    adaptedAlerts.add(adaptedAlert);
    adaptedAlerts.add(adaptedReference);
    ArrayList<Alert> linked = new ReferenceLinker(unAdaptedAlerts, adaptedAlerts).linkReferences();
    assertFalse(linked.get(0).isReplaced());
  }

  @Test
  public void linkReferences_OneAlertIsAdded_AlertNotReplaced() {
    UnadaptedAlert alert = new UnadaptedAlert();
    ArrayList<UnadaptedAlert> unAdaptedAlerts = new ArrayList<>();
    unAdaptedAlerts.add(alert);
    ArrayList<Alert> adaptedAlerts = new ArrayList<>();
    DefaultAlert adaptedAlert = new DefaultAlert();
    adaptedAlerts.add(adaptedAlert);
    ArrayList<Alert> linked = new ReferenceLinker(unAdaptedAlerts, adaptedAlerts).linkReferences();
    assertFalse(linked.get(0).isReplaced());
  }

  @Test
  public void linkReferences_referenceToDifferentTypeProvided_NotLinked() {
    UnadaptedAlert alert = new UnadaptedAlert();
    UnadaptedAlert reference = new UnadaptedAlert();

    alert.addReferenceId("testID");
    reference.setId("testID");

    ArrayList<UnadaptedAlert> unAdaptedAlerts = new ArrayList<>();
    unAdaptedAlerts.add(alert);
    unAdaptedAlerts.add(reference);

    ArrayList<Alert> adaptedAlerts = new ArrayList<>();
    WinterStormWarning adaptedAlert = new WinterStormWarning();
    WinterStormWatch adaptedReference = new WinterStormWatch();
    adaptedReference.setNwsId("testID");
    adaptedAlerts.add(adaptedAlert);
    adaptedAlerts.add(adaptedReference);
    ArrayList<Alert> linked = new ReferenceLinker(unAdaptedAlerts, adaptedAlerts).linkReferences();
    assertEquals(0, linked.get(0).getReferences().size());
  }
}
