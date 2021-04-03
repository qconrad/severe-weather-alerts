package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert.Type;
import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.Preferences.ChannelPreferences;

import static com.severeweatheralerts.Alerts.Alert.Type.POST;

public class RipleEdit {
  private final ChannelPreferences channelPreferences;

  public RipleEdit(ChannelPreferences channelPreferences) {
    this.channelPreferences = channelPreferences;
  }

  public void horizontalRiple(String alertName, Type type, Channel channel) {
    channelPreferences.setChannel(alertName, type, channel);
    if (type == POST && channel.ordinal() < channelPreferences.getChannel(alertName, Type.UPDATE).ordinal())
      channelPreferences.setChannel(alertName, Type.UPDATE, channel);
    if (channel.ordinal() < channelPreferences.getChannel(alertName, Type.CANCEL).ordinal())
      channelPreferences.setChannel(alertName, Type.CANCEL, channel);
  }
}
