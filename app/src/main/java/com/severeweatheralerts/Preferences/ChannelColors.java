package com.severeweatheralerts.Preferences;

import android.graphics.Color;

public class ChannelColors {
  public static int getChannelColor(Channel channel) {
    if (channel == Channel.EXTREME) return Color.parseColor("#9c36b5");
    else if (channel == Channel.HIGH) return Color.parseColor("#c92a2a");
    else if (channel == Channel.MEDIUM) return Color.parseColor("#ccaa2f");
    else if (channel == Channel.LOW) return Color.parseColor("#2b8a3e");
    else return Color.parseColor("#868e96");
  }
}
