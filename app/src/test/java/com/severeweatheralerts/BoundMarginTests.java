package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.Bounds.BoundMargin;
import com.severeweatheralerts.Graphics.Bounds.Bounds;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoundMarginTests {
  @Test
  public void getBounds_MarginOfZero_SameBoundsReturned() {
    Bounds bounds = new Bounds(0, 0, 0, 0);
    BoundMargin boundMargin = new BoundMargin(bounds, 0);
    assertEquals(0.0, boundMargin.getBounds().getLeft(), 0.001);
  }

  @Test
  public void getBounds_MarginOfZeroDifferentLeft_SameBoundsReturned() {
    Bounds bounds = new Bounds(0, 0, 0, 1);
    BoundMargin boundMargin = new BoundMargin(bounds, 0);
    assertEquals(1.0, boundMargin.getBounds().getLeft(), 0.001);
  }

  @Test
  public void getBounds_50PercentMargin_LeftBoundCorrect() {
    Bounds bounds = new Bounds(0, 0, 0, 10);
    BoundMargin boundMargin = new BoundMargin(bounds, 0.5);
    assertEquals(15.0, boundMargin.getBounds().getLeft(), 0.001);
  }

  @Test
  public void getBounds_50PercentMargin_RightBoundCorrect() {
    Bounds bounds = new Bounds(0, 0, 0, -10);
    BoundMargin boundMargin = new BoundMargin(bounds, 0.5);
    assertEquals(5.0, boundMargin.getBounds().getRight(), 0.001);
  }

  @Test
  public void getBounds_OffCenter50PercentMargin_RightBoundCorrect() {
    Bounds bounds = new Bounds(0, 15, 0, 5);
    BoundMargin boundMargin = new BoundMargin(bounds, 0.5);
    assertEquals(20.0, boundMargin.getBounds().getRight(), 0.001);
  }

  @Test
  public void getBounds_OffCenter50PercentMargin_LeftBoundCorrect() {
    Bounds bounds = new Bounds(0, 15, 0, 5);
    BoundMargin boundMargin = new BoundMargin(bounds, 0.5);
    assertEquals(0.0, boundMargin.getBounds().getLeft(), 0.001);
  }

  @Test
  public void getBounds_50PercentMargin_TopBoundCorrect() {
    Bounds bounds = new Bounds(10, 0, 0, 0);
    BoundMargin boundMargin = new BoundMargin(bounds, 0.5);
    assertEquals(15.0, boundMargin.getBounds().getTop(), 0.001);
  }

  @Test
  public void getBounds_50PercentMargin_BottomBoundCorrect() {
    Bounds bounds = new Bounds(10, 0, 0, 0);
    BoundMargin boundMargin = new BoundMargin(bounds, 0.5);
    assertEquals(-5, boundMargin.getBounds().getBottom(), 0.001);
  }
}
