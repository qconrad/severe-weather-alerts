package com.severeweatheralerts.Preferences;

import com.severeweatheralerts.Alerts.Alert;

public class ChannelPreferences {
  public ChannelPreferences() {
  }

  public Channel getChannel(int locationIndex, Alert.Type type, String name) {
    return Channel.HIGH;
  }
}
