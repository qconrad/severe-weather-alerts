package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.Bounds.BoundRounder;
import com.severeweatheralerts.Graphics.Bounds.Bounds;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoundRounderTests {
  @Test
  public void getBounds_TopRounded() {
    Bounds bounds = new Bounds(25.5, 0, 0, 0);
    BoundRounder boundRounder = new BoundRounder(bounds);
    assertEquals(26, boundRounder.getBounds().getTop(), 0.001);
  }
  @Test

  public void getBounds_RightRounded() {
    Bounds bounds = new Bounds(25.5, 23.5, 0, 0);
    BoundRounder boundRounder = new BoundRounder(bounds);
    assertEquals(24, boundRounder.getBounds().getRight(), 0.001);
  }

  @Test
  public void getBounds_BottomRounded() {
    Bounds bounds = new Bounds(25.5, 23.5, 22.5, 0);
    BoundRounder boundRounder = new BoundRounder(bounds);
    assertEquals(23, boundRounder.getBounds().getBottom(), 0.001);
  }

  @Test
  public void getBounds_LeftRounded() {
    Bounds bounds = new Bounds(25.5, 23.5, 22.5, 21.5);
    BoundRounder boundRounder = new BoundRounder(bounds);
    assertEquals(22, boundRounder.getBounds().getLeft(), 0.001);
  }
}
