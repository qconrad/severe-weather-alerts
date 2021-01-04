package com.severeweatheralerts;

import android.graphics.Color;

public class ColorBrightnessChanger {
  public static int changeBrightness(int color, float factor) {
    int a = Color.alpha(color);
    int r = Math.round(Color.red(color) * factor);
    int g = Math.round(Color.green(color) * factor);
    int b = Math.round(Color.blue(color) * factor);
    return Color.argb(a, Math.min(r,255), Math.min(g,255), Math.min(b,255));
  }
}
