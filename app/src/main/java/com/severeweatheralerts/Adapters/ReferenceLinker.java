package com.severeweatheralerts.Adapters;

import com.severeweatheralerts.Alerts.Alert;

import java.util.ArrayList;
import java.util.Collections;

public class ReferenceLinker {
  private final ArrayList<UnadaptedAlert> unadaptedAlerts;
  private final ArrayList<Alert> adaptedAlerts;
  public ReferenceLinker(ArrayList<UnadaptedAlert> unadaptedAlerts, ArrayList<Alert> adaptedAlerts) {
    this.unadaptedAlerts = unadaptedAlerts;
    this.adaptedAlerts = adaptedAlerts;
  }

  public ArrayList<Alert> linkReferences() {
    for (int i = 0; i < unadaptedAlerts.size(); i++)
      checkReferences(i);
    return adaptedAlerts;
  }

  private void checkReferences(int i) {
    if (notReplaced(adaptedAlerts.get(i))) {
      linkReferences(i);
    }
  }

  private void linkReferences(int i) {
    for (int r = 0; r < unadaptedAlerts.get(i).getReferenceCount(); r++) {
      checkReference(i, r);
    }
    Collections.sort(adaptedAlerts.get(i).getReferences());
  }

  private void checkReference(int i, int r) {
    Alert reference = findAlertById(unadaptedAlerts.get(i).getReference(r));
    if (notNull(reference) && sameType(i, reference))
      linkReference(i, reference);
  }

  private void linkReference(int i, Alert reference) {
    Alert alert = adaptedAlerts.get(i);
    alert.addReference(reference);
    reference.setReplacedBy(alert);
    reference.setReplaced(true);
  }

  private boolean sameType(int i, Alert reference) {
    return adaptedAlerts.get(i).getClass().equals(reference.getClass());
  }

  private boolean notReplaced(Alert reference) {
    return !reference.isReplaced();
  }

  private Alert findAlertById(String id) {
    for (int i = 0; i < adaptedAlerts.size(); i++)
      if (idsMatch(id, i)) return adaptedAlerts.get(i);
    return null;
  }

  private boolean idsMatch(String id, int i) {
    return hasId(adaptedAlerts.get(i)) && adaptedAlerts.get(i).getNwsId().equals(id);
  }

  private boolean notNull(Alert alert) {
    return alert != null;
  }

  private boolean hasId(Alert alert) {
    return alert.getNwsId() != null;
  }
}
