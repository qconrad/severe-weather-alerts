package com.severeweatheralerts.Status;

public class ClearWithRecent extends Clear {
  @Override
  public String getSubtext() {
    return super.getSubtext() + " Recently active alerts are shown below.";
  }
}