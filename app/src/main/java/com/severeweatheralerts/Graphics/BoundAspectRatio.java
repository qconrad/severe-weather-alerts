package com.severeweatheralerts.Graphics;

public class BoundAspectRatio {
  private final Bounds bounds;
  private final boolean widerThanTall;
  private final double width;
  private final double height;

  public BoundAspectRatio(Bounds bounds) {
    this.bounds = bounds;
    width = bounds.getRight() - bounds.getLeft();
    height = bounds.getTop() - bounds.getBottom();
    widerThanTall = width > height;
  }

  public Bounds equalize() {
    if (widerThanTall) return equalizeHeight();
    else return equalizeWidth();
  }

  private Bounds equalizeWidth() {
    double axisDifference = (height - width) / 2;
    return new Bounds(bounds.getTop(), bounds.getRight() + axisDifference, bounds.getBottom(), bounds.getLeft() - axisDifference);
  }

  private Bounds equalizeHeight() {
    double axisDifference = (width - height) / 2;
    return new Bounds(bounds.getTop() + axisDifference, bounds.getRight(), bounds.getBottom() - axisDifference, bounds.getLeft());
  }
}
