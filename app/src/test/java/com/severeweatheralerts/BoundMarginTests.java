package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.Bounds.BoundMargin;
import com.severeweatheralerts.Graphics.Bounds.Bound;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoundMarginTests {
  @Test
  public void getBounds_MarginOfZero_SameBoundsReturned() {
    Bound bounds = new Bound(0, 0, 0, 0);
    BoundMargin boundMargin = new BoundMargin(bounds, 0);
    assertEquals(0.0, boundMargin.getBounds().getLeft(), 0.001);
  }

  @Test
  public void getBounds_MarginOfZeroDifferentLeft_SameBoundsReturned() {
    Bound bounds = new Bound(0, 0, 0, 1);
    BoundMargin boundMargin = new BoundMargin(bounds, 0);
    assertEquals(1.0, boundMargin.getBounds().getLeft(), 0.001);
  }

  @Test
  public void getBounds_50PercentMargin_LeftBoundCorrect() {
    Bound bounds = new Bound(0, 0, 0, 10);
    BoundMargin boundMargin = new BoundMargin(bounds, 0.5);
    assertEquals(15.0, boundMargin.getBounds().getLeft(), 0.001);
  }

  @Test
  public void getBounds_50PercentMargin_RightBoundCorrect() {
    Bound bounds = new Bound(0, 0, 0, -10);
    BoundMargin boundMargin = new BoundMargin(bounds, 0.5);
    assertEquals(5.0, boundMargin.getBounds().getRight(), 0.001);
  }

  @Test
  public void getBounds_OffCenter50PercentMargin_RightBoundCorrect() {
    Bound bounds = new Bound(0, 15, 0, 5);
    BoundMargin boundMargin = new BoundMargin(bounds, 0.5);
    assertEquals(20.0, boundMargin.getBounds().getRight(), 0.001);
  }

  @Test
  public void getBounds_OffCenter50PercentMargin_LeftBoundCorrect() {
    Bound bounds = new Bound(0, 15, 0, 5);
    BoundMargin boundMargin = new BoundMargin(bounds, 0.5);
    assertEquals(0.0, boundMargin.getBounds().getLeft(), 0.001);
  }

  @Test
  public void getBounds_50PercentMargin_TopBoundCorrect() {
    Bound bounds = new Bound(10, 0, 0, 0);
    BoundMargin boundMargin = new BoundMargin(bounds, 0.5);
    assertEquals(15.0, boundMargin.getBounds().getTop(), 0.001);
  }

  @Test
  public void getBounds_50PercentMargin_BottomBoundCorrect() {
    Bound bounds = new Bound(10, 0, 0, 0);
    BoundMargin boundMargin = new BoundMargin(bounds, 0.5);
    assertEquals(-5, boundMargin.getBounds().getBottom(), 0.001);
  }
}
