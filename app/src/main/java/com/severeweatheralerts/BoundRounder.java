package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.Bounds.Bounds;

public class BoundRounder {
  private final Bounds bounds;

  public BoundRounder(Bounds bounds) {
    this.bounds = bounds;
  }

  public Bounds getBounds() {
    return new Bounds(Math.round(bounds.getTop()),
            Math.round(bounds.getRight()),
            Math.round(bounds.getBottom()),
            Math.round(bounds.getLeft()));
  }
}
