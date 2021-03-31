package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;

public class ChannelPreferences {
  public ChannelPreferences() {
  }

  public Channel getChannel(Alert.Type type, String name) {
    return Channel.HIGH;
  }
}
