package com.severeweatheralerts.Graphics;

public class BoundCalculator {
  private final Polygon polygon;
  private Bounds bounds = new Bounds();

  public BoundCalculator(Polygon polygon) {
    this.polygon = polygon;
    calculateBounds();
  }

  public BoundCalculator(Polygon polygon, Bounds bounds) {
    this.polygon = polygon;
    this.bounds = bounds;
    calculateBounds();
  }

  public Bounds getBounds() {
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
    bounds = new Bounds(top, right, bottom, left);
  }
}
