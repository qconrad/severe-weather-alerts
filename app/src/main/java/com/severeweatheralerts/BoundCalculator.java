package com.severeweatheralerts;

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
    double top = Double.MIN_VALUE;
    double right = Double.MIN_VALUE;
    double bottom = Double.MAX_VALUE;
    double left = Double.MAX_VALUE;
    for (int i = 0; i < polygon.getCoordinateCount(); i++) {
      if (polygon.getCoordinate(i).getX() < left)
        left = polygon.getCoordinate(i).getX();
      if (polygon.getCoordinate(i).getY() < bottom)
        bottom = polygon.getCoordinate(i).getY();
      if (polygon.getCoordinate(i).getY() > top)
        top = polygon.getCoordinate(i).getY();
      if (polygon.getCoordinate(i).getX() > right)
        right = polygon.getCoordinate(i).getX();
    }
    bounds = new Bounds(top, right, bottom, left);
  }
}
