package com.severeweatheralerts.Adapters;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.UnadaptedAlert;

import java.util.ArrayList;
import java.util.Collections;

public class ReferenceLinker {
  private ArrayList<UnadaptedAlert> unadaptedAlerts;
  private ArrayList<Alert> adaptedAlerts;
  public ReferenceLinker(ArrayList<UnadaptedAlert> unadaptedAlerts, ArrayList<Alert> adaptedAlerts) {
    this.unadaptedAlerts = unadaptedAlerts;
    this.adaptedAlerts = adaptedAlerts;
  }

  public ArrayList<Alert> linkReferences() {
    for (int i = 0; i < unadaptedAlerts.size(); i++) {
      if (notReplaced(adaptedAlerts.get(i))) {
        for (int r = 0; r < unadaptedAlerts.get(i).getReferenceCount(); r++) {
          Alert reference = findAlertById(unadaptedAlerts.get(i).getReference(r));
          if (notNull(reference)) {
            adaptedAlerts.get(i).addReference(reference);
            reference.setReplaced(true);
          }
        }
        Collections.sort(adaptedAlerts.get(i).getReferences());
      }
    }
    return adaptedAlerts;
  }

  private boolean notReplaced(Alert reference) {
    return !reference.isReplaced();
  }

  private Alert findAlertById(String id) {
    for (int i = 0; i < adaptedAlerts.size(); i++) {
      if (hasId(adaptedAlerts.get(i)) && adaptedAlerts.get(i).getNwsId().equals(id))
        return adaptedAlerts.get(i);
    }
    return null;
  }

  private boolean notNull(Alert alert) {
    return alert != null;
  }

  private boolean hasId(Alert alert) {
    return alert.getNwsId() != null;
  }
}
