package com.severeweatheralerts;

import java.util.ArrayList;
import java.util.Collections;

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
      if (notReferenced(adaptedAlerts.get(i))) {
        for (int r = 0; r < unadaptedAlerts.get(i).getReferenceCount(); r++) {
          Alert reference = findAlertById(unadaptedAlerts.get(i).getReference(r));
          if (reference != null) {
            adaptedAlerts.get(i).addReference(reference);
            alreadyReferenced.add(reference);
          }
        }
        Collections.sort(adaptedAlerts.get(i).getReferences());
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
