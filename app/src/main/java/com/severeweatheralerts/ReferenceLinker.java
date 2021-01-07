package com.severeweatheralerts;

import java.util.ArrayList;

public class ReferenceLinker {
  private ArrayList<UnadaptedAlert> unadaptedAlerts;
  private ArrayList<Alert> adaptedAlerts;
  private ArrayList<Alert> alreadyReferenced = new ArrayList<>();
  public ReferenceLinker(ArrayList<UnadaptedAlert> unadaptedAlerts, ArrayList<Alert> adaptedAlerts) {
    this.unadaptedAlerts = unadaptedAlerts;
    this.adaptedAlerts = adaptedAlerts;
  }

  public ArrayList<Alert> linkReferences() {
    for (int i = 0; i < unadaptedAlerts.size(); i++) {
      for (int r = 0; r < unadaptedAlerts.get(i).getReferenceCount(); r++) {
        Alert reference = findAlertById(unadaptedAlerts.get(i).getReference(r));
        if (notReferenced(reference)) {
          adaptedAlerts.get(i).addReference(reference);
          alreadyReferenced.add(reference);
        }
      }
    }
    return adaptedAlerts;
  }

  private boolean notReferenced(Alert reference) {
    return !alreadyReferenced.contains(reference);
  }

  private Alert findAlertById(String id) {
    for (int i = 0; i < adaptedAlerts.size(); i++) {
      if (hasId(adaptedAlerts.get(i)) && adaptedAlerts.get(i).getNwsId().equals(id))
        return adaptedAlerts.get(i);
    }
    return null;
  }

  private boolean hasId(Alert alert) {
    return alert.getNwsId() != null;
  }
}
