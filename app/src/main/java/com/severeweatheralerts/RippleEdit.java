package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert.Type;
import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.Preferences.ChannelPreferences;

import static com.severeweatheralerts.Alerts.Alert.Type.*;
import static com.severeweatheralerts.Alerts.Alert.Type.POST;

public class RippleEdit {
  private final ChannelPreferences channelPreferences;
  private final String[] alertList;

  public RippleEdit(ChannelPreferences channelPreferences, String[] alertList) {
    this.channelPreferences = channelPreferences;
    this.alertList = alertList;
  }

  public void horizontalRipple(String alertName, Type type, Channel channel) {
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

  public void verticalRipple(int index, Type type, Channel channel) {
    channelPreferences.setChannel(alertList[index], type, channel);
    horizontalRipple(alertList[index], type, channel);
    for (int i = 0; i < alertList.length; i++) {
      if (i < index && channelPreferences.getChannel(alertList[i], type).ordinal() < channel.ordinal())
        horizontalRipple(alertList[i], type, channel);
      if (i > index && channelPreferences.getChannel(alertList[i], type).ordinal() > channel.ordinal())
        horizontalRipple(alertList[i], type, channel);
    }
  }
}
