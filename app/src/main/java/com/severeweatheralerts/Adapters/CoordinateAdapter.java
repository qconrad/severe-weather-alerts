package com.severeweatheralerts.Adapters;

public class CoordinateAdapter {
  final static double RADIUS = 6378137.0;
  public static double lat2y(double aLat) {
    return Math.log(Math.tan(Math.PI / 4 + Math.toRadians(aLat) / 2)) * RADIUS;
  }

  public static double lon2x(double aLong) {
    return Math.toRadians(aLong) * RADIUS;
  }

  public static double y2lat(double aY) {
    return Math.toDegrees(Math.atan(Math.exp(aY / RADIUS)) * 2 - Math.PI/2);
  }

  public static double x2lon(double aX) {
    return Math.toDegrees(aX / RADIUS);
  }
}
