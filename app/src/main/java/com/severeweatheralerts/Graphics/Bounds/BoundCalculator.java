package com.severeweatheralerts.Graphics.Bounds;

import com.severeweatheralerts.Graphics.Polygon.Polygon;

public class BoundCalculator {
  private final Polygon polygon;
  private Bound bounds = new Bound();

  public BoundCalculator(Polygon polygon) {
    this.polygon = polygon;
    calculateBounds();
  }

  public BoundCalculator(Polygon polygon, Bound bounds) {
    this.polygon = polygon;
    this.bounds = bounds;
    calculateBounds();
  }

  public Bound getBounds() {
    return bounds;
  }

  private void calculateBounds() {
    double top = bounds.getTop();
    double right = bounds.getRight();
    double bottom = bounds.getBottom();
    double left = bounds.getLeft();
    for (int i = 0; i < polygon.getCoordinateCount(); i++) {
      left = Math.min(left, polygon.getCoordinate(i).getX());
      bottom = Math.min(bottom, polygon.getCoordinate(i).getY());
      top = Math.max(top, polygon.getCoordinate(i).getY());
      right = Math.max(right, polygon.getCoordinate(i).getX());
    }
    bounds = new Bound(top, right, bottom, left);
  }
}
