package com.severeweatheralerts.Graphics.Polygon;

import com.severeweatheralerts.Graphics.Bounds.BoundCalculator;
import com.severeweatheralerts.Graphics.Bounds.Bounds;

import java.util.ArrayList;

public class PolygonListBoundCalculator {
  private final ArrayList<Polygon> polygons;
  private Bounds bounds;

  public PolygonListBoundCalculator(ArrayList<Polygon> polygons) {
    this.polygons = polygons;
    bounds = new Bounds();
  }

  public Bounds getBounds() {
    ArrayList<Polygon> polygonsCopy = new ArrayList<>(polygons);
    for (Polygon polygon : polygonsCopy) addPolygonToBoundCalculation(polygon);
    return bounds;
  }

  private void addPolygonToBoundCalculation(Polygon polygon) {
    bounds = new BoundCalculator(polygon, bounds).getBounds();
  }
}
