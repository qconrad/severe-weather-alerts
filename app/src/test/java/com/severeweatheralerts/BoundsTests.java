package com.severeweatheralerts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoundsTests {
  @Test
  public void getTop_TopPointProvided_CorrectReturned() {
    PolygonBounds polygonBounds = new PolygonBounds(1.0, 0.0, 0.0, 0.0);
    assertEquals(1.0, polygonBounds.getTop(), 0.001);
  }

  @Test
  public void getTop_DifferentTopPointProvided_CorrectReturned() {
    PolygonBounds polygonBounds = new PolygonBounds(2.0, 0.0, 0.0, 1.1);
    assertEquals(2.0, polygonBounds.getTop(), 0.001);
  }

  @Test
  public void getRight_RightPointProvided_CorrectReturned() {
    PolygonBounds polygonBounds = new PolygonBounds(0.0, 1.0, 0.0, 0.0);
    assertEquals(1.0, polygonBounds.getRight(), 0.001);
  }

  @Test
  public void getRight_DifferentRightPointProvided_CorrectReturned() {
    PolygonBounds polygonBounds = new PolygonBounds(0.0, 2.0, 0.0, 0.0);
    assertEquals(2.0, polygonBounds.getRight(), 0.001);
  }

  @Test
  public void getBottom_BottomPointProvided_CorrectReturned() {
    PolygonBounds polygonBounds = new PolygonBounds(0.0, 0.0, 1.0, 0.0);
    assertEquals(1.0, polygonBounds.getBottom(), 0.001);
  }

  @Test
  public void getBottom_DifferentBottomPointProvided_CorrectReturned() {
    PolygonBounds polygonBounds = new PolygonBounds(0.0, 0.0, 2.0, 0.0);
    assertEquals(2.0, polygonBounds.getBottom(), 0.001);
  }

  @Test
  public void getLeft_LeftPointProvided_CorrectReturned() {
    PolygonBounds polygonBounds = new PolygonBounds(0.0, 0.0, 0.0, 0.1);
    assertEquals(0.1, polygonBounds.getLeft(), 0.001);
  }

  @Test
  public void getLeft_DifferentLeftPointProvided_CorrectReturned() {
    PolygonBounds polygonBounds = new PolygonBounds(0.0, 0.0, 0.0, 1.1);
    assertEquals(1.1, polygonBounds.getLeft(), 0.001);
  }
}
