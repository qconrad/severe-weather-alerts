package com.severeweatheralerts.Graphics.Bounds;

public class Bound {
  private final double top;
  private final double right;
  private final double bottom;
  private final double left;

  public Bound(double top, double right, double bottom, double left) {
    this.top = top;
    this.right = right;
    this.bottom = bottom;
    this.left = left;
  }

  public Bound() {
    this.top = Double.NEGATIVE_INFINITY;
    this.right = Double.NEGATIVE_INFINITY;
    this.bottom = Double.POSITIVE_INFINITY;
    this.left = Double.POSITIVE_INFINITY;
  }

  public double getTop() { return top; }
  public double getRight() { return right; }
  public double getBottom() { return bottom; }
  public double getLeft() { return left; }
}
