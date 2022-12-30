package com.severeweatheralerts.Preferences;

public class ChannelPreferencesDataHolder {
  private static ChannelPreferencesDataHolder instance;
  private ChannelPreferences channelPreferences;

  public static ChannelPreferencesDataHolder getInstance() {
    if (instance == null) instance = new ChannelPreferencesDataHolder();
    return instance;
  }

  public ChannelPreferences getChannelPreferences() {
    return channelPreferences;
  }

  public void setChannelPreferences(ChannelPreferences channelPreferences) {
    this.channelPreferences = channelPreferences;
  }
}
