package com.severeweatheralerts;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class ColorMap {
  private final ArrayList<Integer> colors;
  private final double maxValue;

  public ColorMap(ArrayList<Integer> colors, double maxValue) {
    this.colors = colors;
    this.maxValue = maxValue;
  }

  public int getValue(double value) {
    double index = ((colors.size()-1)*((value / maxValue)));
    if (index % 1 == 0) return colors.get((int) index);
    else return interpolateColor(index);
  }

  private int interpolateColor(double index) {
    double percent = index % 1;
    int first = colors.get((int) Math.floor(index));
    int second = colors.get((int) Math.ceil(index));
    int red = (int) Math.round(Color.red(first) + (Color.red(second) - Color.red(first)) * percent);
    int green = (int) Math.round(Color.green(first) + (Color.green(second) - Color.green(first)) * percent);
    int blue = (int) Math.round(Color.blue(first) + (Color.blue(second) - Color.blue(first)) * percent);
    int alpha = (int) Math.round(Color.alpha(first) + (Color.alpha(second) - Color.alpha(first)) * percent);
    return Color.argb(alpha, red, green, blue);
  }
}
