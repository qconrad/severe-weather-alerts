package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.Bounds.BoundCalculator;
import com.severeweatheralerts.Graphics.Bounds.Bound;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.Polygon.Polygon;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoundCalculatorTests {
  @Test
  public void getLeft_OneCoordinateGiven_LeftBoundIsX() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(1.0, 0.0));
    Bound bounds = new BoundCalculator(polygon).getBounds();
    assertEquals(1.0, bounds.getLeft(), 0.001);
  }

  @Test
  public void getLeft_DifferentCoordinateGiven_LeftBoundIsX() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(2.0, 0.0));
    Bound bounds = new BoundCalculator(polygon).getBounds();
    assertEquals(2.0, bounds.getLeft(), 0.001);
  }

  @Test
  public void getLeft_TwoCoordinatesGiven_SmallerXIsLeftBound() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(2.0, 0.0));
    polygon.addCoordinate(new MercatorCoordinate(1.0, 0.0));
    Bound bounds = new BoundCalculator(polygon).getBounds();
    assertEquals(1.0, bounds.getLeft(), 0.001);
  }

  @Test
  public void getLeft_ThreeCoordinatesGiven_SmallestXIsLeftBound() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(-1.0, 0.0));
    polygon.addCoordinate(new MercatorCoordinate(2.0, 0.0));
    polygon.addCoordinate(new MercatorCoordinate(1.0, 0.0));
    Bound bounds = new BoundCalculator(polygon).getBounds();
    assertEquals(-1.0, bounds.getLeft(), 0.001);
  }

  @Test
  public void getBottom_ThreeCoordinatesGiven_SmallestYIsLeftBound() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(0.0, 2.0));
    polygon.addCoordinate(new MercatorCoordinate(0.0, 1.0));
    polygon.addCoordinate(new MercatorCoordinate(0.0, 3.0));
    Bound bounds = new BoundCalculator(polygon).getBounds();
    assertEquals(1.0, bounds.getBottom(), 0.001);
  }

  @Test
  public void getBottom_DifferentCoordinatesGiven_SmallestYIsLeftBound() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(0.0, 2.0));
    polygon.addCoordinate(new MercatorCoordinate(0.0, 1.0));
    polygon.addCoordinate(new MercatorCoordinate(0.0, -3.0));
    Bound bounds = new BoundCalculator(polygon).getBounds();
    assertEquals(-3.0, bounds.getBottom(), 0.001);
  }

  @Test
  public void getTop_CoordinatesGiven_LargestXIsTopBound() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(0.0, 2.0));
    polygon.addCoordinate(new MercatorCoordinate(0.0, 1.0));
    polygon.addCoordinate(new MercatorCoordinate(0.0, -3.0));
    Bound bounds = new BoundCalculator(polygon).getBounds();
    assertEquals(2.0, bounds.getTop(), 0.001);
  }

  @Test
  public void getTop_DifferentCoordinatesGiven_LargestYIsTopBound() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(0.0, 3.0));
    polygon.addCoordinate(new MercatorCoordinate(0.0, 2.0));
    polygon.addCoordinate(new MercatorCoordinate(0.0, -5.0));
    Bound bounds = new BoundCalculator(polygon).getBounds();
    assertEquals(3.0, bounds.getTop(), 0.001);
  }

  @Test
  public void getRight_CoordinatesGiven_LargestXIsRightBound() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(1.0, 0.0));
    polygon.addCoordinate(new MercatorCoordinate(3.0, 0.0));
    polygon.addCoordinate(new MercatorCoordinate(-2.0, 0.0));
    Bound bounds = new BoundCalculator(polygon).getBounds();
    assertEquals(3.0, bounds.getRight(), 0.001);
  }

  @Test
  public void getRight_DifferentCoordinatesGiven_LargestXIsRightBound() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(1.0, 0.0));
    polygon.addCoordinate(new MercatorCoordinate(4.0, 0.0));
    polygon.addCoordinate(new MercatorCoordinate(-2.0, 0.0));
    Bound bounds = new BoundCalculator(polygon).getBounds();
    assertEquals(4.0, bounds.getRight(), 0.001);
  }

  @Test
  public void getRight_NegativeRightGiven_CorrectBound() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(-5.0, 0.0));
    Bound bounds = new BoundCalculator(polygon).getBounds();
    assertEquals(-5.0, bounds.getRight(), 0.001);
  }

  @Test
  public void getRight_NegativeTopGiven_CorrectBound() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(0.0, -50.0));
    Bound bounds = new BoundCalculator(polygon).getBounds();
    assertEquals(-50.0, bounds.getTop(), 0.001);
  }
}
