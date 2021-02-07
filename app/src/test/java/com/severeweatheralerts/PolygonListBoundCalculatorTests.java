package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.MercatorCoordinate;
import com.severeweatheralerts.Graphics.Polygon;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PolygonListBoundCalculatorTests {
  @Test
  public void getBounds_PolygonProvided_BoundsIsNotNull() {
    ArrayList<Polygon> polygons = new ArrayList<>();
    Polygon polygon =  new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(0.0, 0.0));
    polygons.add(polygon);
    PolygonListBoundCalculator boundListCalculator = new PolygonListBoundCalculator(polygons);
    assertEquals(0.0, boundListCalculator.getBounds().getLeft(), 0.001);
  }

  @Test
  public void getBounds_PolygonProvided_LeftIsCorrect() {
    ArrayList<Polygon> polygons = new ArrayList<>();
    Polygon polygon =  new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(-1.0, 0));
    polygons.add(polygon);
    PolygonListBoundCalculator boundListCalculator = new PolygonListBoundCalculator(polygons);
    assertEquals(-1.0, boundListCalculator.getBounds().getLeft(), 0.001);
  }

  @Test
  public void getBounds_PolygonProvided_RightIsCorrect() {
    ArrayList<Polygon> polygons = new ArrayList<>();
    Polygon polygon =  new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(5.0, 0));
    polygons.add(polygon);
    PolygonListBoundCalculator boundListCalculator = new PolygonListBoundCalculator(polygons);
    assertEquals(5.0, boundListCalculator.getBounds().getRight(), 0.001);
  }

  @Test
  public void getBounds_PolygonProvided_TopIsCorrect() {
    ArrayList<Polygon> polygons = new ArrayList<>();
    Polygon polygon =  new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(5.0, 1.0));
    polygons.add(polygon);
    PolygonListBoundCalculator boundListCalculator = new PolygonListBoundCalculator(polygons);
    assertEquals(1.0, boundListCalculator.getBounds().getTop(), 0.001);
  }

  @Test
  public void getBounds_PolygonProvided_BottomIsCorrect() {
    ArrayList<Polygon> polygons = new ArrayList<>();
    Polygon polygon =  new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(5.0, -2.0));
    polygons.add(polygon);
    PolygonListBoundCalculator boundListCalculator = new PolygonListBoundCalculator(polygons);
    assertEquals(-2.0, boundListCalculator.getBounds().getTop(), 0.001);
  }

  @Test
  public void getBounds_TwoPolygonsProvided_LeftIsCorrect() {
    ArrayList<Polygon> polygons = new ArrayList<>();
    Polygon polygon =  new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(5.0, -2.0));
    polygons.add(polygon);
    Polygon polygon2 =  new Polygon();
    polygon2.addCoordinate(new MercatorCoordinate(-3.0, -2.0));
    polygons.add(polygon2);
    PolygonListBoundCalculator boundListCalculator = new PolygonListBoundCalculator(polygons);
    assertEquals(-3.0, boundListCalculator.getBounds().getLeft(), 0.001);
  }

  @Test
  public void getBounds_TwoPolygonsProvidedButFlipped_LeftIsCorrect() {
    ArrayList<Polygon> polygons = new ArrayList<>();
    Polygon polygon =  new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(-3.0, -2.0));
    polygons.add(polygon);
    Polygon polygon2 =  new Polygon();
    polygon2.addCoordinate(new MercatorCoordinate(5.0, -2.0));
    polygons.add(polygon2);
    PolygonListBoundCalculator boundListCalculator = new PolygonListBoundCalculator(polygons);
    assertEquals(-3.0, boundListCalculator.getBounds().getLeft(), 0.001);
  }
}
