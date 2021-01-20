package com.severeweatheralerts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PolygonBoundCalculatorTests {
  @Test
  public void getLeft_OneCoordinateGiven_LeftBoundIsX() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(1.0, 0.0));
    PolygonBoundCalculator polygonBoundCalculator = new PolygonBoundCalculator(polygon);
    assertEquals(1.0, polygonBoundCalculator.getLeft(), 0.001);
  }

  @Test
  public void getLeft_DifferentCoordinateGiven_LeftBoundIsX() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(2.0, 0.0));
    PolygonBoundCalculator polygonBoundCalculator = new PolygonBoundCalculator(polygon);
    assertEquals(2.0, polygonBoundCalculator.getLeft(), 0.001);
  }

  @Test
  public void getLeft_TwoCoordinatesGiven_SmallerXIsLeftBound() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(2.0, 0.0));
    polygon.addCoordinate(new MercatorCoordinate(1.0, 0.0));
    PolygonBoundCalculator polygonBoundCalculator = new PolygonBoundCalculator(polygon);
    assertEquals(1.0, polygonBoundCalculator.getLeft(), 0.001);
  }

  @Test
  public void getLeft_ThreeCoordinatesGiven_SmallestXIsLeftBound() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(-1.0, 0.0));
    polygon.addCoordinate(new MercatorCoordinate(2.0, 0.0));
    polygon.addCoordinate(new MercatorCoordinate(1.0, 0.0));
    PolygonBoundCalculator polygonBoundCalculator = new PolygonBoundCalculator(polygon);
    assertEquals(-1.0, polygonBoundCalculator.getLeft(), 0.001);
  }

  @Test
  public void getBottom_ThreeCoordinatesGiven_SmallestYIsLeftBound() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(0.0, 2.0));
    polygon.addCoordinate(new MercatorCoordinate(0.0, 1.0));
    polygon.addCoordinate(new MercatorCoordinate(0.0, 3.0));
    PolygonBoundCalculator polygonBoundCalculator = new PolygonBoundCalculator(polygon);
    assertEquals(1.0, polygonBoundCalculator.getBottom(), 0.001);
  }

  @Test
  public void getBottom_DifferentCoordinatesGiven_SmallestYIsLeftBound() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(0.0, 2.0));
    polygon.addCoordinate(new MercatorCoordinate(0.0, 1.0));
    polygon.addCoordinate(new MercatorCoordinate(0.0, -3.0));
    PolygonBoundCalculator polygonBoundCalculator = new PolygonBoundCalculator(polygon);
    assertEquals(-3.0, polygonBoundCalculator.getBottom(), 0.001);
  }

  @Test
  public void getTop_CoordinatesGiven_LargestXIsTopBound() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(0.0, 2.0));
    polygon.addCoordinate(new MercatorCoordinate(0.0, 1.0));
    polygon.addCoordinate(new MercatorCoordinate(0.0, -3.0));
    PolygonBoundCalculator polygonBoundCalculator = new PolygonBoundCalculator(polygon);
    assertEquals(2.0, polygonBoundCalculator.getTop(), 0.001);
  }

  @Test
  public void getTop_DifferentCoordinatesGiven_LargestYIsTopBound() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(0.0, 3.0));
    polygon.addCoordinate(new MercatorCoordinate(0.0, 2.0));
    polygon.addCoordinate(new MercatorCoordinate(0.0, -5.0));
    PolygonBoundCalculator polygonBoundCalculator = new PolygonBoundCalculator(polygon);
    assertEquals(3.0, polygonBoundCalculator.getTop(), 0.001);
  }

  @Test
  public void getRight_CoordinatesGiven_LargestXIsRightBound() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(1.0, 0.0));
    polygon.addCoordinate(new MercatorCoordinate(3.0, 0.0));
    polygon.addCoordinate(new MercatorCoordinate(-2.0, 0.0));
    PolygonBoundCalculator polygonBoundCalculator = new PolygonBoundCalculator(polygon);
    assertEquals(3.0, polygonBoundCalculator.getRight(), 0.001);
  }

  @Test
  public void getRight_DifferentCoordinatesGiven_LargestXIsRightBound() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(1.0, 0.0));
    polygon.addCoordinate(new MercatorCoordinate(4.0, 0.0));
    polygon.addCoordinate(new MercatorCoordinate(-2.0, 0.0));
    PolygonBoundCalculator polygonBoundCalculator = new PolygonBoundCalculator(polygon);
    assertEquals(4.0, polygonBoundCalculator.getRight(), 0.001);
  }
}
