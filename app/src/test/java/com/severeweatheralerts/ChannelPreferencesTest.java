package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.Preferences.ChannelPreferences;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChannelPreferencesTest {
  @Test
  public void getChannel_TornadoWarningPost_defaultOfExtremeReturn() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    assertEquals(Channel.EXTREME, channelPreferences.getChannel(0, Alert.Type.POST, "Tornado Warning"));
  }

  @Test
  public void getChannel_TornadoWarningUpdate_defaultOfHigh() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    assertEquals(Channel.HIGH, channelPreferences.getChannel(0, Alert.Type.UPDATE, "Tornado Warning"));
  }

  @Test
  public void getChannel_TornadoWarningCancel_defaultOfHigh() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    assertEquals(Channel.HIGH, channelPreferences.getChannel(0, Alert.Type.CANCEL, "Tornado Warning"));
  }

  @Test
  public void getChannel_InvalidAlert_defaultOfMedium() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    assertEquals(Channel.MEDIUM, channelPreferences.getChannel(0, Alert.Type.CANCEL, "Random Invalid Alert Name"));
  }
}
