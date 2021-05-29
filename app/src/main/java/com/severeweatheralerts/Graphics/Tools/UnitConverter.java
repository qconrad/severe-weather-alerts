package com.severeweatheralerts.Graphics.Tools;

public class UnitConverter {
  public static double kphToMph(double value) { return value / 1.609; }
  public static double mmToIn(double value) { return value / 25.4; }
  public static double cToF(double value) { return value * (9.0/5.0) + 32; }
}
