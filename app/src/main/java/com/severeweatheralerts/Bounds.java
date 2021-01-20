package com.severeweatheralerts;

public class Bounds {
  private final double top;
  private final double right;
  private final double bottom;
  private final double left;

  public Bounds(double top, double right, double bottom, double left) {
    this.top = top;
    this.right = right;
    this.bottom = bottom;
    this.left = left;
  }

  public double getTop() { return top; }
  public double getRight() { return right; }
  public double getBottom() { return bottom; }
  public double getLeft() { return left; }
}
