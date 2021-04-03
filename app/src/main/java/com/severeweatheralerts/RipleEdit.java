package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert.Type;
import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.Preferences.ChannelPreferences;

import static com.severeweatheralerts.Alerts.Alert.Type.*;
import static com.severeweatheralerts.Alerts.Alert.Type.POST;

public class RipleEdit {
  private final ChannelPreferences channelPreferences;

  public RipleEdit(ChannelPreferences channelPreferences) {
    this.channelPreferences = channelPreferences;
  }

  public void horizontalRiple(String alertName, Type type, Channel channel) {
    channelPreferences.setChannel(alertName, type, channel);
    if (type == POST && channel.ordinal() < channelPreferences.getChannel(alertName, UPDATE).ordinal())
      channelPreferences.setChannel(alertName, UPDATE, channel);
    if (channel.ordinal() < channelPreferences.getChannel(alertName, CANCEL).ordinal())
      channelPreferences.setChannel(alertName, CANCEL, channel);
    if (type == CANCEL && channelPreferences.getChannel(alertName, UPDATE).ordinal() < channel.ordinal())
      channelPreferences.setChannel(alertName, UPDATE, channel);
    if (channelPreferences.getChannel(alertName, POST).ordinal() < channel.ordinal())
      channelPreferences.setChannel(alertName, POST, channel);
  }
}
