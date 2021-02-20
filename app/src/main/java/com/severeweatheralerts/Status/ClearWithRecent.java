package com.severeweatheralerts.Status;

import java.util.ArrayList;

public class ClearWithRecent extends Clear {
  @Override
  public ArrayList<String> getSubtext() {
    subtexts.set(0, super.getSubtext().get(0) + " Recently active alerts are shown below.");
    return subtexts;
  }
}
