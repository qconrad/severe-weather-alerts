package com.severeweatheralerts.Status;

import java.util.ArrayList;

public class ClearWithRecent extends Clear {
  @Override
  public ArrayList<String> getSubtexts() {
    super.getSubtexts();
    subtexts.add("Recently active alerts shown below");
    return subtexts;
  }
}
