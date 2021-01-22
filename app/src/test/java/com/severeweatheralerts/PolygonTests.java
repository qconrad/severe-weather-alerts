package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.MercatorCoordinate;
import com.severeweatheralerts.Graphics.Polygon;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PolygonTests {
  @Test
  public void getCoordinateCount_NothingAdded_CountIs0() {
    assertEquals(0, new Polygon().getCoordinateCount());
  }

  @Test
  public void getCoordinateCount_OneCoordinateAdded_CountIs1() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(0.0, 0.0));
    assertEquals(1, polygon.getCoordinateCount());
  }

  @Test
  public void getCoordinateCount_TwoCoordinateAdded_CountIs2() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(0.0, 0.0));
    polygon.addCoordinate(new MercatorCoordinate(0.0, 0.0));
    assertEquals(2, polygon.getCoordinateCount());
  }

  @Test
  public void getCoordinate_OneCoordinateIsAdded_CorrectCoordinateReturn() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(1.0, 2.0));
    assertEquals(1, polygon.getCoordinate(0).getX(), 0.001);
    assertEquals(2, polygon.getCoordinate(0).getY(), 0.001);
  }

  @Test
  public void getCoordinate_DifferentCoordinateIsAdded_CorrectCoordinateReturn() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(2.0, 1.0));
    assertEquals(2, polygon.getCoordinate(0).getX(), 0.001);
    assertEquals(1, polygon.getCoordinate(0).getY(), 0.001);
  }

  @Test
  public void getCoordinate_TwoCoordinatesIsAdded_FirstCorrectCoordinateReturn() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(2.0, 1.0));
    polygon.addCoordinate(new MercatorCoordinate(1.0, 2.0));
    assertEquals(2, polygon.getCoordinate(0).getX(), 0.001);
    assertEquals(1, polygon.getCoordinate(0).getY(), 0.001);
  }

  @Test
  public void getCoordinate_DifferentCoordinateIsAdded_FirstCorrectCoordinateReturn() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(2.0, 1.0));
    polygon.addCoordinate(new MercatorCoordinate(1.0, 2.0));
    assertEquals(2, polygon.getCoordinate(0).getX(), 0.001);
    assertEquals(1, polygon.getCoordinate(0).getY(), 0.001);
  }

  @Test
  public void getCoordinate_DifferentCoordinateIsAdded_SecondCorrectCoordinateReturn() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(2.0, 1.0));
    polygon.addCoordinate(new MercatorCoordinate(1.0, 2.0));
    assertEquals(1, polygon.getCoordinate(1).getX(), 0.001);
    assertEquals(2, polygon.getCoordinate(1).getY(), 0.001);
  }
}
