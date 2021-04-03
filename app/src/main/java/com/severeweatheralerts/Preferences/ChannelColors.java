package com.severeweatheralerts.Preferences;

import android.graphics.Color;

public class ChannelColors {
  public static int getChannelColor(Channel channel) {
    if (channel == Channel.EXTREME) return Color.parseColor("#FF00FF");
    else if (channel == Channel.HIGH) return Color.parseColor("#FF0000");
    else if (channel == Channel.MEDIUM) return Color.parseColor("#FFFF00");
    else if (channel == Channel.LOW) return Color.parseColor("#00FF00");
    else return Color.parseColor("#CCCCCC");
  }
}
