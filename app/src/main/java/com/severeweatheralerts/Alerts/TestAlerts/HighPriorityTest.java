package com.severeweatheralerts.Alerts.TestAlerts;

import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.Preferences.ChannelColors;
import com.severeweatheralerts.TestAlert;

public class HighPriorityTest extends TestAlert {
  public HighPriorityTest() {
    super();
    setName("High Priority Test");
  }

  @Override
  public int getColor() {
    return ChannelColors.getChannelColor(Channel.HIGH);
  }
}
