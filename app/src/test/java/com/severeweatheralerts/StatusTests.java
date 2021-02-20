package com.severeweatheralerts;

import org.junit.Test;

public class StatusTests {
  @Test
  public void clearImplementsStatus() {
    Status clear = new Clear();
    clear.getIcon();
    clear.getHeadline();
    clear.getSubtext();
  }
}
