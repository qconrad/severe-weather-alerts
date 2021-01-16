package com.severeweatheralerts.RecyclerViews.Reference;

import com.severeweatheralerts.Alerts.Alert;

import java.util.Date;

public class ReferenceColorChooser {
  private final Alert reference;

  public ReferenceColorChooser(Alert reference) {
    this.reference = reference;
  }

  public int getColorAt(Date date) {
    return reference.getReplacedBy().getColorAt(date);
  }
}
