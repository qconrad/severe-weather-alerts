package com.severeweatheralerts.Graphics;

import android.graphics.Point;

public class MercatorCoordinateToPointAdapter {
  private final Bounds bounds;
  private final int length;
  private final int height;

  public MercatorCoordinateToPointAdapter(Bounds bounds, int length, int height) {
    this.bounds = bounds;
    this.length = length;
    this.height = height;
  }

  public Point getPoint(MercatorCoordinate coordinate) {
    return new Point(getX(coordinate), getY(coordinate));
  }

  private int getX(MercatorCoordinate coordinate) {
    return (int)(((coordinate.getX() - bounds.getLeft()) / (bounds.getRight() - bounds.getLeft())) * length);
  }

  private int getY(MercatorCoordinate coordinate) {
    return (int)(((coordinate.getY() - bounds.getBottom()) / (bounds.getTop() - bounds.getBottom())) * height);
  }
}
