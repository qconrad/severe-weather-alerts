package com.severeweatheralerts.Alerts.TestAlerts;

import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.Preferences.ChannelColors;
import com.severeweatheralerts.TestAlert;

public class MediumPriorityTest extends TestAlert {
  public MediumPriorityTest() {
    super();
    setName("Medium Priority Test");
  }

  @Override
  public int getColor() {
    return ChannelColors.getChannelColor(Channel.MEDIUM);
  }
}
