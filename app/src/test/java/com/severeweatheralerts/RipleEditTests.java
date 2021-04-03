package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.Preferences.ChannelPreferences;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RipleEditTests {
  @Test
  public void tornadoPostSetToHigh_returnsHigh() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    new RipleEdit(channelPreferences).horizontalRiple("Tornado Warning", Alert.Type.POST, Channel.HIGH);
    assertEquals(Channel.HIGH, channelPreferences.getChannel("Tornado Warning", Alert.Type.POST));
  }

  @Test
  public void tornadoWarningDowngraded_UpdateAlsoChanged() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    new RipleEdit(channelPreferences).horizontalRiple("Tornado Warning", Alert.Type.POST, Channel.MEDIUM);
    assertEquals(Channel.MEDIUM, channelPreferences.getChannel("Tornado Warning", Alert.Type.UPDATE));
  }

  @Test
  public void snowSquallDowngraded_UpdateAlsoChanged() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    RipleEdit ripleEdit = new RipleEdit(channelPreferences);
    ripleEdit.horizontalRiple("Snow Squall Warning", Alert.Type.POST, Channel.MEDIUM);
    assertEquals(Channel.MEDIUM, channelPreferences.getChannel("Snow Squall Warning", Alert.Type.UPDATE));
  }

  @Test
  public void snowSquallDowngradedToLow_UpdateAlsoChanged() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    RipleEdit ripleEdit = new RipleEdit(channelPreferences);
    ripleEdit.horizontalRiple("Snow Squall Warning", Alert.Type.POST, Channel.LOW);
    assertEquals(Channel.LOW, channelPreferences.getChannel("Snow Squall Warning", Alert.Type.UPDATE));
  }

  @Test
  public void snowSquallDowngradedToNone_CancelAlsoChanged() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    RipleEdit ripleEdit = new RipleEdit(channelPreferences);
    ripleEdit.horizontalRiple("Snow Squall Warning", Alert.Type.POST, Channel.NONE);
    assertEquals(Channel.NONE, channelPreferences.getChannel("Snow Squall Warning", Alert.Type.CANCEL));
  }

  @Test
  public void HydroUpgradedToExtreme_UpdateUntouched() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    RipleEdit ripleEdit = new RipleEdit(channelPreferences);
    ripleEdit.horizontalRiple("Hydrologic Outlook", Alert.Type.POST, Channel.EXTREME);
    assertEquals(Channel.MEDIUM, channelPreferences.getChannel("Hydrologic Outlook", Alert.Type.UPDATE));
  }

  @Test
  public void HydroUpgradedToHigh_UpdateUntouched() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    RipleEdit ripleEdit = new RipleEdit(channelPreferences);
    ripleEdit.horizontalRiple("Hydrologic Outlook", Alert.Type.POST, Channel.HIGH);
    assertEquals(Channel.MEDIUM, channelPreferences.getChannel("Hydrologic Outlook", Alert.Type.UPDATE));
  }

  @Test
  public void windUpgradedToExtreme_UpdateUntouched() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Wind Advisory", Alert.Type.POST, Channel.NONE);
    channelPreferences.setChannel("Wind Advisory", Alert.Type.UPDATE, Channel.NONE);
    RipleEdit ripleEdit = new RipleEdit(channelPreferences);
    ripleEdit.horizontalRiple("Wind Advisory", Alert.Type.POST, Channel.MEDIUM);
    assertEquals(Channel.NONE, channelPreferences.getChannel("Wind Advisory", Alert.Type.UPDATE));
  }

  @Test
  public void HydroUpgradedToExtreme_CancelUntouched() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    RipleEdit ripleEdit = new RipleEdit(channelPreferences);
    ripleEdit.horizontalRiple("Hydrologic Outlook", Alert.Type.POST, Channel.EXTREME);
    assertEquals(Channel.LOW, channelPreferences.getChannel("Hydrologic Outlook", Alert.Type.CANCEL));
  }

  @Test
  public void tornadoWarningUpdateUpgraded_ReturnCorrect() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    RipleEdit ripleEdit = new RipleEdit(channelPreferences);
    ripleEdit.horizontalRiple("Tornado Warning", Alert.Type.UPDATE, Channel.EXTREME);
    assertEquals(Channel.EXTREME, channelPreferences.getChannel("Tornado Warning", Alert.Type.UPDATE));
  }

  @Test
  public void tornadoWarningCancelSetToLow_UpdateUntouched() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    RipleEdit ripleEdit = new RipleEdit(channelPreferences);
    ripleEdit.horizontalRiple("Tornado Warning", Alert.Type.CANCEL, Channel.LOW);
    assertEquals(Channel.HIGH, channelPreferences.getChannel("Tornado Warning", Alert.Type.UPDATE));
  }
}
