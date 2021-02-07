package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.Bounds;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoundAspectRatioTests {
  @Test
  public void equalize_alreadyEqualized_LeftBoundSame() {
    BoundAspectRatio boundAspectRatio = new BoundAspectRatio(new Bounds(0, 0, 0, 0));
    assertEquals(0.0, boundAspectRatio.equalize().getLeft(), 0.001);
  }

  @Test
  public void equalize_WiderThanTall_TopAdjusted() {
    BoundAspectRatio boundAspectRatio = new BoundAspectRatio(new Bounds(5, 10, -5, -10));
    assertEquals(10.0, boundAspectRatio.equalize().getTop(), 0.001);
  }

  @Test
  public void equalize_MoreWiderThanTall_TopAdjusted() {
    BoundAspectRatio boundAspectRatio = new BoundAspectRatio(new Bounds(5, 15, -5, -15));
    assertEquals(15.0, boundAspectRatio.equalize().getTop(), 0.001);
  }

  @Test
  public void equalize_WiderThanTallButOffsetFromOrigin_TopAdjusted() {
    BoundAspectRatio boundAspectRatio = new BoundAspectRatio(new Bounds(5, 20, -5, 0));
    assertEquals(10.0, boundAspectRatio.equalize().getTop(), 0.001);
  }

  @Test
  public void equalize_WiderThanTallButOffsetFromOrigin_BottomAdjusted() {
    BoundAspectRatio boundAspectRatio = new BoundAspectRatio(new Bounds(5, 20, -5, 0));
    assertEquals(-10.0, boundAspectRatio.equalize().getBottom(), 0.001);
  }

  @Test
  public void equalize_WiderThanTall_LeftNotTouched() {
    BoundAspectRatio boundAspectRatio = new BoundAspectRatio(new Bounds(5, 20, -5, 5));
    assertEquals(5.0, boundAspectRatio.equalize().getLeft(), 0.001);
  }

  @Test
  public void equalize_WiderThanTall_RightNotTouched() {
    BoundAspectRatio boundAspectRatio = new BoundAspectRatio(new Bounds(5, 20, -5, 0));
    assertEquals(20.0, boundAspectRatio.equalize().getRight(), 0.001);
  }

  @Test
  public void equalize_TallerThanWide_LeftAdjusted() {
    BoundAspectRatio boundAspectRatio = new BoundAspectRatio(new Bounds(10, 5, -10, -5));
    assertEquals(-10, boundAspectRatio.equalize().getLeft(), 0.001);
  }

  @Test
  public void equalize_TallerThanWide_RightAdjusted() {
    BoundAspectRatio boundAspectRatio = new BoundAspectRatio(new Bounds(10, 5, -10, -5));
    assertEquals(10, boundAspectRatio.equalize().getRight(), 0.001);
  }

  @Test
  public void equalize_TallerThanWide_TopNotTouched() {
    BoundAspectRatio boundAspectRatio = new BoundAspectRatio(new Bounds(10, 5, -10, -5));
    assertEquals(10, boundAspectRatio.equalize().getTop(), 0.001);
  }

  @Test
  public void equalize_TallerThanWide_BottomNotTouched() {
    BoundAspectRatio boundAspectRatio = new BoundAspectRatio(new Bounds(10, 5, -10, -5));
    assertEquals(-10, boundAspectRatio.equalize().getBottom(), 0.001);
  }
}
