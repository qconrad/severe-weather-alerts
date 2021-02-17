package com.severeweatheralerts.Graphics.Polygon;

import com.severeweatheralerts.Graphics.Bounds.BoundCalculator;
import com.severeweatheralerts.Graphics.Bounds.Bound;

import java.util.ArrayList;

public class PolygonListBoundCalculator {
  private final ArrayList<Polygon> polygons;
  Bound bounds;

  public PolygonListBoundCalculator(ArrayList<Polygon> polygons) {
    this.polygons = polygons;
    bounds = new Bound();
  }

  public Bound getBounds() {
    for (Polygon polygon : polygons) addPolygonToBoundCalculation(polygon);
    return bounds;
  }

  private void addPolygonToBoundCalculation(Polygon polygon) {
    bounds = new BoundCalculator(polygon, bounds).getBounds();
  }
}
