package com.severeweatheralerts.Alerts.TestAlerts;

import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.Preferences.ChannelColors;
import com.severeweatheralerts.TestAlert;

public class LowPriorityTest extends TestAlert {
  public LowPriorityTest() {
    super();
    setName("Low Priority Test");
  }

  @Override
  public int getColor() {
    return ChannelColors.getChannelColor(Channel.LOW);
  }
}