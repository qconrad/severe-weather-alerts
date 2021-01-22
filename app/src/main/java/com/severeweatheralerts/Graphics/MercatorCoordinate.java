package com.severeweatheralerts.Graphics;

public class MercatorCoordinate {
  private final double x;
  private final double y;

  public MercatorCoordinate(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }
}
