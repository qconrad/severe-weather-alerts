package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.Preferences.ChannelPreferences;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RippleEditTests {
  @Test
  public void tornadoPostSetToHigh_returnsHigh() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    new RippleEdit(channelPreferences, null).horizontalRipple("Tornado Warning", Alert.Type.POST, Channel.HIGH);
    assertEquals(Channel.HIGH, channelPreferences.getChannel("Tornado Warning", Alert.Type.POST));
  }

  @Test
  public void tornadoWarningDowngraded_UpdateAlsoChanged() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    new RippleEdit(channelPreferences, null).horizontalRipple("Tornado Warning", Alert.Type.POST, Channel.MEDIUM);
    assertEquals(Channel.MEDIUM, channelPreferences.getChannel("Tornado Warning", Alert.Type.UPDATE));
  }

  @Test
  public void snowSquallDowngraded_UpdateAlsoChanged() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, null);
    rippleEdit.horizontalRipple("Snow Squall Warning", Alert.Type.POST, Channel.MEDIUM);
    assertEquals(Channel.MEDIUM, channelPreferences.getChannel("Snow Squall Warning", Alert.Type.UPDATE));
  }

  @Test
  public void snowSquallDowngradedToLow_UpdateAlsoChanged() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, null);
    rippleEdit.horizontalRipple("Snow Squall Warning", Alert.Type.POST, Channel.LOW);
    assertEquals(Channel.LOW, channelPreferences.getChannel("Snow Squall Warning", Alert.Type.UPDATE));
  }

  @Test
  public void snowSquallDowngradedToNone_CancelAlsoChanged() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, null);
    rippleEdit.horizontalRipple("Snow Squall Warning", Alert.Type.POST, Channel.NONE);
    assertEquals(Channel.NONE, channelPreferences.getChannel("Snow Squall Warning", Alert.Type.CANCEL));
  }

  @Test
  public void HydroUpgradedToExtreme_UpdateUntouched() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, null);
    rippleEdit.horizontalRipple("Hydrologic Outlook", Alert.Type.POST, Channel.EXTREME);
    assertEquals(Channel.MEDIUM, channelPreferences.getChannel("Hydrologic Outlook", Alert.Type.UPDATE));
  }

  @Test
  public void HydroUpgradedToHigh_UpdateUntouched() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, null);
    rippleEdit.horizontalRipple("Hydrologic Outlook", Alert.Type.POST, Channel.HIGH);
    assertEquals(Channel.MEDIUM, channelPreferences.getChannel("Hydrologic Outlook", Alert.Type.UPDATE));
  }

  @Test
  public void windUpgradedToExtreme_UpdateUntouched() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Wind Advisory", Alert.Type.POST, Channel.NONE);
    channelPreferences.setChannel("Wind Advisory", Alert.Type.UPDATE, Channel.NONE);
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, null);
    rippleEdit.horizontalRipple("Wind Advisory", Alert.Type.POST, Channel.MEDIUM);
    assertEquals(Channel.NONE, channelPreferences.getChannel("Wind Advisory", Alert.Type.UPDATE));
  }

  @Test
  public void HydroUpgradedToExtreme_CancelUntouched() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, null);
    rippleEdit.horizontalRipple("Hydrologic Outlook", Alert.Type.POST, Channel.EXTREME);
    assertEquals(Channel.LOW, channelPreferences.getChannel("Hydrologic Outlook", Alert.Type.CANCEL));
  }

  @Test
  public void tornadoWarningUpdateUpgraded_ReturnCorrect() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, null);
    rippleEdit.horizontalRipple("Tornado Warning", Alert.Type.UPDATE, Channel.EXTREME);
    assertEquals(Channel.EXTREME, channelPreferences.getChannel("Tornado Warning", Alert.Type.UPDATE));
  }

  @Test
  public void tornadoWarningCancelSetToLow_UpdateUntouched() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, null);
    rippleEdit.horizontalRipple("Tornado Warning", Alert.Type.CANCEL, Channel.LOW);
    assertEquals(Channel.HIGH, channelPreferences.getChannel("Tornado Warning", Alert.Type.UPDATE));
  }

  @Test
  public void windUpdateSetToExtreme_PostUpgraded() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, null);
    rippleEdit.horizontalRipple("Wind Advisory", Alert.Type.UPDATE, Channel.EXTREME);
    assertEquals(Channel.EXTREME, channelPreferences.getChannel("Wind Advisory", Alert.Type.POST));
  }

  @Test
  public void windCancelSetToExtreme_UpdateUpgraded() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, null);
    rippleEdit.horizontalRipple("Wind Advisory", Alert.Type.CANCEL, Channel.EXTREME);
    assertEquals(Channel.EXTREME, channelPreferences.getChannel("Wind Advisory", Alert.Type.UPDATE));
  }

  @Test
  public void newPostSmallerThanUpdateButNotCancel_UpdateCorrect() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Tornado Warning", Alert.Type.CANCEL, Channel.NONE);
    channelPreferences.setChannel("Tornado Warning", Alert.Type.UPDATE, Channel.MEDIUM);
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, null);
    rippleEdit.horizontalRipple("Wind Advisory", Alert.Type.POST, Channel.LOW);
    assertEquals(Channel.LOW, channelPreferences.getChannel("Wind Advisory", Alert.Type.UPDATE));
  }

  @Test
  public void tornadoDowngraded_snowSquallAlsoDowngraded() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    String[] alertList = {"Tornado Warning", "Snow Squall Warning"};
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, alertList);
    rippleEdit.verticalRipple(0, Alert.Type.POST, Channel.HIGH);
    assertEquals(Channel.HIGH, channelPreferences.getChannel("Snow Squall Warning", Alert.Type.POST));
  }

  @Test
  public void tornadoDowngraded_dustStormAlsoDowngraded() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    String[] alertList = {"Tornado Warning", "Dust Storm Warning"};
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, alertList);
    rippleEdit.verticalRipple(0, Alert.Type.POST, Channel.HIGH);
    assertEquals(Channel.HIGH, channelPreferences.getChannel("Dust Storm Warning", Alert.Type.POST));
  }

  @Test
  public void tornadoDowngraded_3Alerts_SnowSquallDowngraded() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    String[] alertList = {"Tornado Warning", "Dust Storm Warning", "Snow Squall Warning"};
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, alertList);
    rippleEdit.verticalRipple(0, Alert.Type.POST, Channel.HIGH);
    assertEquals(Channel.HIGH, channelPreferences.getChannel("Snow Squall Warning", Alert.Type.POST));
  }

  @Test
  public void tornadoDowngraded_3Alerts_SnowSquallDowngradedToMedium() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    String[] alertList = {"Tornado Warning", "Dust Storm Warning", "Snow Squall Warning"};
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, alertList);
    rippleEdit.verticalRipple(0, Alert.Type.POST, Channel.MEDIUM);
    assertEquals(Channel.MEDIUM, channelPreferences.getChannel("Snow Squall Warning", Alert.Type.POST));
  }

  @Test
  public void tornadoUpdateDowngraded_3Alerts_SnowSquallUpdateDowngradedToMedium() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    String[] alertList = {"Tornado Warning", "Dust Storm Warning", "Snow Squall Warning"};
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, alertList);
    rippleEdit.verticalRipple(0, Alert.Type.UPDATE, Channel.MEDIUM);
    assertEquals(Channel.MEDIUM, channelPreferences.getChannel("Snow Squall Warning", Alert.Type.UPDATE));
  }

  @Test
  public void windAdvisoryDowngraded_TornadoWarningUntouched() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    String[] alertList = {"Tornado Warning", "Wind Advisory", "Severe Thunderstorm Watch"};
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, alertList);
    rippleEdit.verticalRipple(1, Alert.Type.POST, Channel.LOW);
    assertEquals(Channel.EXTREME, channelPreferences.getChannel("Tornado Warning", Alert.Type.POST));
  }

  @Test
  public void windAdvisoryUpgraded_SevereAlsoUpgraded() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    String[] alertList = {"Severe Thunderstorm Watch", "Wind Advisory"};
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, alertList);
    rippleEdit.verticalRipple(1, Alert.Type.POST, Channel.EXTREME);
    assertEquals(Channel.EXTREME, channelPreferences.getChannel("Severe Thunderstorm Watch", Alert.Type.POST));
  }

  @Test
  public void index1SetToExtreme_alertAtThatIndexExtreme() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    String[] alertList = {"Severe Thunderstorm Watch", "Wind Advisory"};
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, alertList);
    rippleEdit.verticalRipple(1, Alert.Type.POST, Channel.EXTREME);
    assertEquals(Channel.EXTREME, channelPreferences.getChannel("Wind Advisory", Alert.Type.POST));
  }

  @Test
  public void tornadoWarningSetToNone_CancelSetToNone() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    String[] alertList = {"Tornado Warning"};
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, alertList);
    rippleEdit.verticalRipple(0, Alert.Type.POST, Channel.NONE);
    assertEquals(Channel.NONE, channelPreferences.getChannel("Tornado Warning", Alert.Type.CANCEL));
  }

  @Test
  public void tornadoWarningSetToNone_WindCancelSetToNone() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    String[] alertList = {"Tornado Warning", "Wind Advisory"};
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, alertList);
    rippleEdit.verticalRipple(0, Alert.Type.POST, Channel.NONE);
    assertEquals(Channel.NONE, channelPreferences.getChannel("Wind Advisory", Alert.Type.CANCEL));
  }

  @Test
  public void windAdvisoryCancelSetToExtreme_SevereWatchPostSetToExtreme() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    String[] alertList = {"Severe Thunderstorm Watch", "Wind Advisory"};
    RippleEdit rippleEdit = new RippleEdit(channelPreferences, alertList);
    rippleEdit.verticalRipple(1, Alert.Type.CANCEL, Channel.EXTREME);
    assertEquals(Channel.EXTREME, channelPreferences.getChannel("Severe Thunderstorm Watch", Alert.Type.POST));
  }
}
