package com.severeweatheralerts.Graphics;

public class BoundCalculator {
  private final Polygon polygon;
  private Bounds bounds;

  public BoundCalculator(Polygon polygon) {
    this.polygon = polygon;
    calculateBounds();
  }

  public Bounds getBounds() {
    return bounds;
  }

  private void calculateBounds() {
    double top = Double.NEGATIVE_INFINITY;
    double right = Double.NEGATIVE_INFINITY;
    double bottom = Double.POSITIVE_INFINITY;
    double left = Double.POSITIVE_INFINITY;
    for (int i = 0; i < polygon.getCoordinateCount(); i++) {
      left = Math.min(left, polygon.getCoordinate(i).getX());
      bottom = Math.min(bottom, polygon.getCoordinate(i).getY());
      top = Math.max(top, polygon.getCoordinate(i).getY());
      right = Math.max(right, polygon.getCoordinate(i).getX());
    }
    bounds = new Bounds(top, right, bottom, left);
  }
}
