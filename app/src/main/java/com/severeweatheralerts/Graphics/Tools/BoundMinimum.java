package com.severeweatheralerts.Graphics.Tools;

import com.severeweatheralerts.Graphics.Bounds.Bounds;

public class BoundMinimum {
  private final Bounds bounds;
  private final double minimumSize;

  public BoundMinimum(Bounds bounds, double minimumSize) {
    this.bounds = bounds;
    this.minimumSize = minimumSize;
  }

  public Bounds getBounds() {
    double widthDifference = minimumSize - bounds.getWidth();
    double widthScaleFactor = (widthDifference / bounds.getWidth()) / 2;
    double heightDifference = minimumSize - bounds.getHeight();
    double heightScaleFactor = (heightDifference / bounds.getHeight()) / 2;

    if (widthDifference < 0 && heightDifference < 0) return bounds;
    else if (widthScaleFactor > heightScaleFactor) {
      return new Bounds(bounds.getTop() + (bounds.getHeight() * widthScaleFactor), bounds.getRight() + (bounds.getWidth() * widthScaleFactor), bounds.getBottom() - (bounds.getHeight() * widthScaleFactor), bounds.getLeft() - (bounds.getWidth() * widthScaleFactor));
    } else if (heightScaleFactor > widthScaleFactor) {
      return new Bounds(bounds.getTop() + (bounds.getHeight() * heightScaleFactor), bounds.getRight() + (bounds.getWidth() * heightScaleFactor), bounds.getBottom() - (bounds.getHeight() * heightScaleFactor), bounds.getLeft() - (bounds.getWidth() * heightScaleFactor));
    } else if (heightScaleFactor == widthScaleFactor) {
      return new Bounds(bounds.getTop() + (bounds.getHeight() * heightScaleFactor), bounds.getRight() + (bounds.getWidth() * widthScaleFactor), bounds.getBottom() - (bounds.getHeight() * heightScaleFactor), bounds.getLeft() - (bounds.getWidth() * widthScaleFactor));
    }

    return bounds;
  }
}
