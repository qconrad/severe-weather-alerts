package com.severeweatheralerts.Adapters;

import com.severeweatheralerts.Alerts.Alert;

import java.util.ArrayList;
import java.util.Collections;

public class ReferenceLinker {
  private final ArrayList<UnadaptedAlert> unadaptedAlerts;
  private final ArrayList<Alert> adaptedAlerts;
  private final ArrayList<Alert> toDelete = new ArrayList<>();
  public ReferenceLinker(ArrayList<UnadaptedAlert> unadaptedAlerts, ArrayList<Alert> adaptedAlerts) {
    this.unadaptedAlerts = unadaptedAlerts;
    this.adaptedAlerts = adaptedAlerts;
  }

  public ArrayList<Alert> linkReferences() {
    for (int i = 0; i < unadaptedAlerts.size(); i++)
      checkReferences(unadaptedAlerts.get(i), adaptedAlerts.get(i));
    deleteNestedCancels();
    return adaptedAlerts;
  }

  private void deleteNestedCancels() {
    for (Alert alert : toDelete) adaptedAlerts.remove(alert);
  }

  private void checkReferences(UnadaptedAlert uaAlert, Alert alert) {
    if (notReplaced(alert)) linkAlertReferences(uaAlert, alert);
  }

  private void linkAlertReferences(UnadaptedAlert next, Alert alert) {
    for (String reference : next.getReferences()) checkReference(reference, alert);
    Collections.sort(alert.getReferences());
  }

  private void checkReference(String referenceID, Alert alert) {
    Alert reference = findAlertById(referenceID);
    if (notNull(reference)) conditionallyLink(alert, reference);
  }

  private void conditionallyLink(Alert alert, Alert reference) {
    if (nestedCancel(reference.getReplacedBy())) {
      if (reference.getReplacedBy().getType() == Alert.Type.CANCEL) {
        toDelete.add(reference.getReplacedBy());
        linkReference(alert, reference);
      } else toDelete.add(alert);
    }
    else if (sameType(alert, reference)) linkReference(alert, reference);
  }

  private boolean nestedCancel(Alert replacedBy) {
    return replacedBy != null;
  }

  private void linkReference(Alert alert, Alert reference) {
    alert.addReference(reference);
    reference.setReplacedBy(alert);
  }

  private boolean sameType(Alert alert, Alert reference) {
    return alert.getClass().equals(reference.getClass());
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
    return nestedCancel(alert);
  }

  private boolean hasId(Alert alert) {
    return alert.getNwsId() != null;
  }
}
