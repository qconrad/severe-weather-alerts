package com.severeweatheralerts.Preferences;

public class ChannelIdString {
  public static String getChannelString(Channel channel) {
    if (channel == Channel.EXTREME) return "ext";
    else if (channel == Channel.HIGH) return "high";
    else if (channel == Channel.MEDIUM) return "med";
    else if (channel == Channel.LOW) return "low";
    return "none";
  }
}
