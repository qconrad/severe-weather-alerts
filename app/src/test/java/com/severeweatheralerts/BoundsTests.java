package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.Bounds.Bound;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoundsTests {
  @Test
  public void getTop_TopPointProvided_CorrectReturned() {
    Bound bounds = new Bound(1.0, 0.0, 0.0, 0.0);
    assertEquals(1.0, bounds.getTop(), 0.001);
  }

  @Test
  public void getTop_DifferentTopPointProvided_CorrectReturned() {
    Bound bounds = new Bound(2.0, 0.0, 0.0, 1.1);
    assertEquals(2.0, bounds.getTop(), 0.001);
  }

  @Test
  public void getRight_RightPointProvided_CorrectReturned() {
    Bound bounds = new Bound(0.0, 1.0, 0.0, 0.0);
    assertEquals(1.0, bounds.getRight(), 0.001);
  }

  @Test
  public void getRight_DifferentRightPointProvided_CorrectReturned() {
    Bound bounds = new Bound(0.0, 2.0, 0.0, 0.0);
    assertEquals(2.0, bounds.getRight(), 0.001);
  }

  @Test
  public void getBottom_BottomPointProvided_CorrectReturned() {
    Bound bounds = new Bound(0.0, 0.0, 1.0, 0.0);
    assertEquals(1.0, bounds.getBottom(), 0.001);
  }

  @Test
  public void getBottom_DifferentBottomPointProvided_CorrectReturned() {
    Bound bounds = new Bound(0.0, 0.0, 2.0, 0.0);
    assertEquals(2.0, bounds.getBottom(), 0.001);
  }

  @Test
  public void getLeft_LeftPointProvided_CorrectReturned() {
    Bound bounds = new Bound(0.0, 0.0, 0.0, 0.1);
    assertEquals(0.1, bounds.getLeft(), 0.001);
  }

  @Test
  public void getLeft_DifferentLeftPointProvided_CorrectReturned() {
    Bound bounds = new Bound(0.0, 0.0, 0.0, 1.1);
    assertEquals(1.1, bounds.getLeft(), 0.001);
  }
}
