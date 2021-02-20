package com.severeweatheralerts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatusTests {
  @Test
  public void clearImplementsStatus() {
    Status clear = new Clear();
    clear.getIcon();
    clear.getHeadline();
    clear.getSubtext();
  }

  @Test
  public void getIcon_ClearStatus_IsSun() {
    Status clear = new Clear();
    assertEquals(R.drawable.sun, clear.getIcon());
  }

  @Test
  public void getHeadline_ClearStatus_CorrectText() {
    Status clear = new Clear();
    assertEquals("You're in the clear!", clear.getHeadline());
  }

  @Test
  public void getSubtext_ClearStatus_CorrectText() {
    Status clear = new Clear();
    assertEquals("There are no active alerts for this location. When hazardous weather is expected, a push notification will be sent and alerts will snow up here.", clear.getSubtext());
  }

  @Test
  public void getSubtext_ClearWithRecentStatus_CorrectText() {
    Status clear = new ClearWithRecent();
    assertEquals("There are no active alerts for this location. When hazardous weather is expected, a push notification will be sent and alerts will snow up here. Recently active alerts are shown below.", clear.getSubtext());
  }
}
