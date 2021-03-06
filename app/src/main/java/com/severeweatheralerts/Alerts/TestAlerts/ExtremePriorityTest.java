package com.severeweatheralerts.Alerts.TestAlerts;

import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.Preferences.ChannelColors;

public class ExtremePriorityTest extends TestAlert {
  public ExtremePriorityTest() {
    super();
    setName("Extreme Priority Test");
  }

  @Override
  public int getColor() {
    return ChannelColors.getChannelColor(Channel.EXTREME);
  }
}
