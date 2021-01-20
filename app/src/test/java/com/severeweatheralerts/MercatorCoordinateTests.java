package com.severeweatheralerts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MercatorCoordinateTests {
  @Test
  public void getX_XIsGiven_XReturn() {
    assertEquals(1.1, new MercatorCoordinate(1.1, 0.0).getX(), 0.001);
  }

  @Test
  public void getX_DifferentXIsGiven_XReturn() {
    assertEquals(1.5, new MercatorCoordinate(1.5, 0.0).getX(), 0.001);
  }

  @Test
  public void getY_YIsGiven_YReturned() {
    assertEquals(1.5, new MercatorCoordinate(0.0, 1.5).getY(), 0.001);
  }

  @Test
  public void getY_DifferentYIsGiven_YReturned() {
    assertEquals(1.6, new MercatorCoordinate(0.6, 1.6).getY(), 0.001);
  }
}
