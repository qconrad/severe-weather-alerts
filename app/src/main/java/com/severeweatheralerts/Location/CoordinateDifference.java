package com.severeweatheralerts.Location;

import com.severeweatheralerts.Adapters.GCSCoordinate;

public class CoordinateDifference {
  private final GCSCoordinate first;
  private final GCSCoordinate second;

  public CoordinateDifference(GCSCoordinate first, GCSCoordinate second) {
    this.first = first;
    this.second = second;
  }

  public boolean isDifferent(double margin) {
    if (latDifferent(margin)) return true;
    return lonDifferent(margin);
  }

  private boolean lonDifferent(double margin) {
    return Math.abs(first.getLong() - second.getLong()) >= margin;
  }

  private boolean latDifferent(double margin) {
    return Math.abs(first.getLat() - second.getLat()) >= margin;
  }
}
