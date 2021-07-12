package com.severeweatheralerts.Status;

import java.util.ArrayList;

public class ClearWithRecent extends Clear {
  public ClearWithRecent() {
    super();
    subtexts.add("Recently active alerts shown below");
  }

  @Override
  public ArrayList<String> getSubtexts() {
    return subtexts;
  }
}
