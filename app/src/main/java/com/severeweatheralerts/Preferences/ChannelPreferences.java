package com.severeweatheralerts.Preferences;

import java.util.HashMap;

import static com.severeweatheralerts.Alerts.Alert.*;
import static com.severeweatheralerts.Alerts.Alert.Type.*;
import static com.severeweatheralerts.Preferences.Channel.*;

public class ChannelPreferences {
  final Channel DEFAULT_UNKNOWN = MEDIUM;

  private final HashMap<String, Channel[]> defaultMap = new HashMap<>();
  private final HashMap<String, Channel> userMap = new HashMap<>();

  public ChannelPreferences() {
    populateDefaults();
  }

  public Channel getChannel(String alertName, Type type) {
    String preferenceString = getPreferenceString(alertName, type);
    if (userMap.containsKey(preferenceString)) return userMap.get(preferenceString);
    return getDefaultMapping(alertName, type);
  }

  private Channel getDefaultMapping(String alertName, Type type) {
    Channel[] alertMap = getAlertMap(alertName);
    if (alertMap == null) return DEFAULT_UNKNOWN;
    else if (type == UPDATE) return getUpdate(alertMap);
    else if (type == CANCEL) return getCancel(alertMap);
    return getPost(alertMap);
  }

  public void setChannel(String alertName, Type type, Channel channel) {
    if (!isDefaultMapping(alertName, type, channel)) addUserMapping(alertName, type, channel);
    else deleteUserMapping(alertName, type);
  }

  private boolean isDefaultMapping(String alertName, Type type, Channel channel) {
    return getDefaultMapping(alertName, type) == channel;
  }

  private void addUserMapping(String alertName, Type type, Channel channel) {
    userMap.put(getPreferenceString(alertName, type), channel);
  }

  private void deleteUserMapping(String alertName, Type type) {
    userMap.remove(getPreferenceString(alertName, type));
  }

  private String getPreferenceString(String alertName, Type type) {
    return new PreferenceStringGenerator(alertName, type).getString();
  }

  public HashMap<String, Channel> getUserMap() {
    return userMap;
  }

  private Channel getPost(Channel[] alertMap) { return alertMap[0]; }
  private Channel getUpdate(Channel[] alertMap) { return alertMap[1]; }
  private Channel getCancel(Channel[] alertMap) { return alertMap[2]; }
  private Channel[] getAlertMap(String name) { return defaultMap.get(name); }

  private void populateDefaults() {
    defaultMap.put("Tornado Warning",                     new Channel[]{EXTREME, HIGH,   HIGH});
    defaultMap.put("Volcano Warning",                     new Channel[]{EXTREME, HIGH,   HIGH});
    defaultMap.put("Extreme Wind Warning",                new Channel[]{EXTREME, HIGH,   HIGH});
    defaultMap.put("Hurricane Warning",                   new Channel[]{EXTREME, HIGH,   HIGH});
    defaultMap.put("Hurricane Force Wind Warning",        new Channel[]{EXTREME, HIGH,   HIGH});
    defaultMap.put("Storm Surge Warning",                 new Channel[]{EXTREME, HIGH,   HIGH});
    defaultMap.put("Tsunami Warning",                     new Channel[]{EXTREME, HIGH,   HIGH});
    defaultMap.put("Typhoon Warning",                     new Channel[]{EXTREME, HIGH,   HIGH});
    defaultMap.put("Snow Squall Warning",                 new Channel[]{EXTREME, HIGH,   HIGH});
    defaultMap.put("Dust Storm Warning",                  new Channel[]{EXTREME, HIGH,   HIGH});
    defaultMap.put("Severe Thunderstorm Warning",         new Channel[]{HIGH,    HIGH,   LOW});
    defaultMap.put("Blizzard Warning",                    new Channel[]{HIGH,    HIGH,   LOW});
    defaultMap.put("Ice Storm Warning",                   new Channel[]{HIGH,    HIGH,   LOW});
    defaultMap.put("Tropical Storm Warning",              new Channel[]{HIGH,    HIGH,   LOW});
    defaultMap.put("Storm Warning",                       new Channel[]{HIGH,    HIGH,   LOW});
    defaultMap.put("Flash Flood Warning",                 new Channel[]{HIGH,    HIGH,   LOW});
    defaultMap.put("Avalanche Warning",                   new Channel[]{HIGH,    HIGH,   LOW});
    defaultMap.put("Flood Warning",                       new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Special Marine Warning",              new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Tornado Watch",                       new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Tsunami Watch",                       new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Blizzard Watch",                      new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Severe Thunderstorm Watch",           new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Hurricane Watch",                     new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Hurricane Force Wind Watch",          new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Heavy Freezing Spray Warning",        new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Lake Effect Snow Warning",            new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Winter Storm Warning",                new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Ashfall Warning",                     new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Earthquake Warning",                  new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Extreme Cold Warning",                new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Wind Chill Warning",                  new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("High Wind Warning",                   new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Excessive Heat Warning",              new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Red Flag Warning",                    new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Extreme Fire Danger",                 new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Special Weather Statement",           new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Tropical Storm Watch",                new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Storm Surge Watch",                   new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Storm Watch",                         new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Typhoon Watch",                       new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Lake Effect Snow Watch",              new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Winter Storm Watch",                  new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Heavy Freezing Spray Watch",          new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Flash Flood Watch",                   new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Extreme Cold Watch",                  new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Wind Chill Watch",                    new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Excessive Heat Watch",                new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("High Wind Watch",                     new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Flood Watch",                         new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Fire Weather Watch",                  new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Avalanche Watch",                     new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Hard Freeze Warning",                 new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Freeze Warning",                      new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Hard Freeze Watch",                   new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Freeze Watch",                        new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Hurricane Local Statement",           new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Typhoon Local Statement",             new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Tropical Depression Local Statement", new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Tropical Storm Local Statement",      new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Tsunami Advisory",                    new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Avalanche Advisory",                  new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Lakeshore Flood Warning",             new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Coastal Flood Warning",               new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("High Surf Warning",                   new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Lake Effect Snow Advisory",           new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Winter Weather Advisory",             new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Freezing Spray Advisory",             new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Freezing Rain Advisory",              new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Freezing Fog Advisory",               new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Heat Advisory",                       new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Wind Chill Advisory",                 new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Wind Advisory",                       new Channel[]{HIGH,    MEDIUM, LOW});
    defaultMap.put("Blowing Dust Advisory",               new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Blowing Dust Warning",                new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Dust Advisory",                       new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Ashfall Advisory",                    new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Dense Smoke Advisory",                new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Dense Fog Advisory",                  new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Gale Warning",                        new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Hazardous Seas Warning",              new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Lakeshore Flood Advisory",            new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Lakeshore Flood Statement",           new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Coastal Flood Advisory",              new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Coastal Flood Statement",             new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Frost Advisory",                      new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("High Surf Advisory",                  new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Rip Current Statement",               new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Beach Hazards Statement",             new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Marine Weather Statement",            new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Gale Watch",                          new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Lakeshore Flood Watch",               new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Hazardous Seas Watch",                new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Coastal Flood Watch",                 new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Small Craft Advisory",                new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Flood Advisory",                      new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Lake Wind Advisory",                  new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Brisk Wind Advisory",                 new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Fire Warning",                        new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Evacuation - Immediate",              new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Civil Danger Warning",                new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Civil Emergency Message",             new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Child Abduction Emergency",           new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Shelter In Place Warning",            new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Nuclear Power Plant Warning",         new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Radiological Hazard Warning",         new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Hazardous Materials Warning",         new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("911 Telephone Outage Emergency",      new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Administrative Message",              new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Law Enforcement Warning",             new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Local Area Emergency",                new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Low Water Advisory",                  new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Air Quality Alert",                   new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Air Stagnation Advisory",             new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Short Term Forecast",                 new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Hydrologic Advisory",                 new Channel[]{MEDIUM,  MEDIUM, LOW});
    defaultMap.put("Hydrologic Outlook",                  new Channel[]{MEDIUM,  MEDIUM, LOW});
  }
}
