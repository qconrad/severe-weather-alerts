package com.severeweatheralerts.Status;

import java.util.ArrayList;

public class ClearWithRecent extends Clear {
  @Override
  public ArrayList<String> getSubtexts() {
    subtexts.set(0, super.getSubtexts().get(0) + " Recently active alerts are shown below.");
    return subtexts;
  }
}
