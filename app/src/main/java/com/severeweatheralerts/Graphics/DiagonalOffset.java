package com.severeweatheralerts.Graphics;

public class DiagonalOffset {
  private final int distance;
  private final int angle;

  public DiagonalOffset(int distance, int angle) {
    this.distance = distance;
    this.angle = angle;
  }

  public double getY() {
    return distance * Math.sin(Math.toRadians(angle - 90));
  }

  public double getX() {
    return distance * Math.cos(Math.toRadians(angle + 90));
  }
}
