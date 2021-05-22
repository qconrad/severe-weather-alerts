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

  @RequiresApi(api = Build.VERSION_CODES.O)
  public int getValue(double value) {
    double index = ((colors.size()-1)*((value / maxValue)));
    if (index % 1 == 0) return colors.get((int) index);
    else return interpolateColor(index);
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  private int interpolateColor(double index) {
    double percent = index % 1;
    Color first = Color.valueOf(colors.get((int) Math.floor(index)));
    Color second = Color.valueOf(colors.get((int) Math.ceil(index)));
    int red = (int) Math.round(Math.abs((first.red() + ((second.red() - first.red())*percent))*255));
    int green = (int) Math.round(Math.abs((first.green() + ((second.green() - first.green())*percent))*255));
    int blue = (int) Math.round(Math.abs((first.blue() + ((second.blue() - first.blue())*percent))*255));
    int alpha = (int) Math.round(Math.abs((first.alpha() + ((second.alpha() - first.alpha())*percent))*255));
    return Color.argb(alpha, red, green, blue);
  }
}
