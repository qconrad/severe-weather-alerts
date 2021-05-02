package com.severeweatheralerts.Adapters;

import com.severeweatheralerts.AlertListTools.AlertFinder;
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
    Alert reference = new AlertFinder(adaptedAlerts).findAlertByID(referenceID);
    if (notNull(reference)) conditionallyLink(alert, reference);
  }

  private void conditionallyLink(Alert alert, Alert reference) {
    if (isCancel(reference)) toDelete.add(reference);
    else linkAndDelete(alert, reference);
  }

  private void linkAndDelete(Alert alert, Alert reference) {
    if (alreadyHasParent(reference)) deleteCancel(alert, reference);
    else if (sameType(alert, reference)) linkReference(alert, reference);
  }

  private void deleteCancel(Alert alert, Alert reference) {
    if (reference.getReplacedBy().getType() == Alert.Type.CANCEL) {
      toDelete.add(reference.getReplacedBy());
      linkReference(alert, reference);
    } else toDelete.add(alert);
  }

  private boolean isCancel(Alert reference) {
    return reference.getType() != null && reference.getType().equals(Alert.Type.CANCEL);
  }

  private boolean alreadyHasParent(Alert replacedBy) {
    return replacedBy.getReplacedBy() != null;
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

  private boolean notNull(Alert alert) {
    return alert != null;
  }
}
