package com.severeweatheralerts.Graphics.Bounds;

public class BoundMargin {
  private final Bounds bounds;
  private final double marginPercentage;

  public BoundMargin(Bounds bounds, double marginPercentage) {
    this.bounds = bounds;
    this.marginPercentage = marginPercentage;
  }

  public Bounds getBounds() {
    return new Bounds(getTop(), getRight(), getBottom(), getLeft());
  }

  protected double getLeft() {
    return bounds.getLeft() - getHorizontalMargin();
  }

  protected double getBottom() {
    return bounds.getBottom() - getVerticalMargin();
  }

  protected double getRight() {
    return bounds.getRight() + getHorizontalMargin();
  }

  protected double getTop() {
    return bounds.getTop() + getVerticalMargin();
  }

  private double getHorizontalMargin() {
    return (bounds.getRight() - bounds.getLeft()) * marginPercentage;
  }

  private double getVerticalMargin() {
    return (bounds.getTop() - bounds.getBottom()) * marginPercentage;
  }
}
