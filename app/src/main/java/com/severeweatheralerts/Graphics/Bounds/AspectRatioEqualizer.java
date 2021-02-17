package com.severeweatheralerts.Graphics.Bounds;

public class AspectRatioEqualizer {
  private final Bound bounds;
  private final boolean widerThanTall;
  private final double width;
  private final double height;

  public AspectRatioEqualizer(Bound bounds) {
    this.bounds = bounds;
    width = bounds.getRight() - bounds.getLeft();
    height = bounds.getTop() - bounds.getBottom();
    widerThanTall = width > height;
  }

  public Bound equalize() {
    if (widerThanTall) return equalizeHeight();
    else return equalizeWidth();
  }

  private Bound equalizeWidth() {
    double axisDifference = (height - width) / 2;
    return new Bound(bounds.getTop(), bounds.getRight() + axisDifference, bounds.getBottom(), bounds.getLeft() - axisDifference);
  }

  private Bound equalizeHeight() {
    double axisDifference = (width - height) / 2;
    return new Bound(bounds.getTop() + axisDifference, bounds.getRight(), bounds.getBottom() - axisDifference, bounds.getLeft());
  }
}
