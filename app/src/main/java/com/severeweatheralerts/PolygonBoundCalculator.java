package com.severeweatheralerts;

public class PolygonBoundCalculator {
  private final Polygon polygon;
  private double left;
  private double bottom;
  private double top;
  private double right;

  public PolygonBoundCalculator(Polygon polygon) {
    this.polygon = polygon;
    calculate();
  }

  public double getLeft() { return left; }
  public double getBottom() { return bottom; }
  public double getTop() { return top; }
  public double getRight() { return right; }

  private void calculate() {
    double minX = Double.MAX_VALUE;
    double minY = Double.MAX_VALUE;
    double maxY = Double.MIN_VALUE;
    double maxX = Double.MIN_VALUE;
    for (int i = 0; i < polygon.getCoordinateCount(); i++) {
      if (polygon.getCoordinate(i).getX() < minX)
        minX = polygon.getCoordinate(i).getX();
      if (polygon.getCoordinate(i).getY() < minY)
        minY = polygon.getCoordinate(i).getY();
      if (polygon.getCoordinate(i).getY() > maxY)
        maxY = polygon.getCoordinate(i).getY();
      if (polygon.getCoordinate(i).getX() > maxX)
        maxX = polygon.getCoordinate(i).getX();
    }
    left = minX;
    bottom = minY;
    top = maxY;
    right = maxX;
  }
}
