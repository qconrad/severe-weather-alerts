package com.severeweatheralerts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoundsTests {
  @Test
  public void getTop_TopPointProvided_CorrectReturned() {
    Bounds bounds = new Bounds(1.0, 0.0, 0.0, 0.0);
    assertEquals(1.0, bounds.getTop(), 0.001);
  }

  @Test
  public void getTop_DifferentTopPointProvided_CorrectReturned() {
    Bounds bounds = new Bounds(2.0, 0.0, 0.0, 1.1);
    assertEquals(2.0, bounds.getTop(), 0.001);
  }

  @Test
  public void getRight_RightPointProvided_CorrectReturned() {
    Bounds bounds = new Bounds(0.0, 1.0, 0.0, 0.0);
    assertEquals(1.0, bounds.getRight(), 0.001);
  }

  @Test
  public void getRight_DifferentRightPointProvided_CorrectReturned() {
    Bounds bounds = new Bounds(0.0, 2.0, 0.0, 0.0);
    assertEquals(2.0, bounds.getRight(), 0.001);
  }

  @Test
  public void getBottom_BottomPointProvided_CorrectReturned() {
    Bounds bounds = new Bounds(0.0, 0.0, 1.0, 0.0);
    assertEquals(1.0, bounds.getBottom(), 0.001);
  }

  @Test
  public void getBottom_DifferentBottomPointProvided_CorrectReturned() {
    Bounds bounds = new Bounds(0.0, 0.0, 2.0, 0.0);
    assertEquals(2.0, bounds.getBottom(), 0.001);
  }

  @Test
  public void getLeft_LeftPointProvided_CorrectReturned() {
    Bounds bounds = new Bounds(0.0, 0.0, 0.0, 0.1);
    assertEquals(0.1, bounds.getLeft(), 0.001);
  }

  @Test
  public void getLeft_DifferentLeftPointProvided_CorrectReturned() {
    Bounds bounds = new Bounds(0.0, 0.0, 0.0, 1.1);
    assertEquals(1.1, bounds.getLeft(), 0.001);
  }
}
