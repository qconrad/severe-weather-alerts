package com.severeweatheralerts.Preferences;

import com.severeweatheralerts.Alerts.Alert.Type;

import static com.severeweatheralerts.Alerts.Alert.Type.CANCEL;
import static com.severeweatheralerts.Alerts.Alert.Type.POST;
import static com.severeweatheralerts.Alerts.Alert.Type.UPDATE;

public class RippleEdit {
  private final ChannelPreferences channelPreferences;

  public RippleEdit(ChannelPreferences channelPreferences) {
    this.channelPreferences = channelPreferences;
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

  public void verticalRipple(String[] alertList, int index, Type type, Channel channel) {
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
