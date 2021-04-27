package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.Bounds.AspectRatioEqualizer;
import com.severeweatheralerts.Graphics.Bounds.Bounds;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoundAspectRatioTests {
  @Test
  public void equalize_alreadyEqualized_LeftBoundSame() {
    AspectRatioEqualizer boundAspectRatio = new AspectRatioEqualizer(new Bounds(0, 0, 0, 0));
    assertEquals(0.0, boundAspectRatio.equalize().getLeft(), 0.001);
  }

  @Test
  public void equalize_WiderThanTall_TopAdjusted() {
    AspectRatioEqualizer boundAspectRatio = new AspectRatioEqualizer(new Bounds(5, 10, -5, -10));
    assertEquals(10.0, boundAspectRatio.equalize().getTop(), 0.001);
  }

  @Test
  public void equalize_MoreWiderThanTall_TopAdjusted() {
    AspectRatioEqualizer boundAspectRatio = new AspectRatioEqualizer(new Bounds(5, 15, -5, -15));
    assertEquals(15.0, boundAspectRatio.equalize().getTop(), 0.001);
  }

  @Test
  public void equalize_WiderThanTallButOffsetFromOrigin_TopAdjusted() {
    AspectRatioEqualizer boundAspectRatio = new AspectRatioEqualizer(new Bounds(5, 20, -5, 0));
    assertEquals(10.0, boundAspectRatio.equalize().getTop(), 0.001);
  }

  @Test
  public void equalize_WiderThanTallButOffsetFromOrigin_BottomAdjusted() {
    AspectRatioEqualizer boundAspectRatio = new AspectRatioEqualizer(new Bounds(5, 20, -5, 0));
    assertEquals(-10.0, boundAspectRatio.equalize().getBottom(), 0.001);
  }

  @Test
  public void equalize_WiderThanTall_LeftNotTouched() {
    AspectRatioEqualizer boundAspectRatio = new AspectRatioEqualizer(new Bounds(5, 20, -5, 5));
    assertEquals(5.0, boundAspectRatio.equalize().getLeft(), 0.001);
  }

  @Test
  public void equalize_WiderThanTall_RightNotTouched() {
    AspectRatioEqualizer boundAspectRatio = new AspectRatioEqualizer(new Bounds(5, 20, -5, 0));
    assertEquals(20.0, boundAspectRatio.equalize().getRight(), 0.001);
  }

  @Test
  public void equalize_TallerThanWide_LeftAdjusted() {
    AspectRatioEqualizer boundAspectRatio = new AspectRatioEqualizer(new Bounds(10, 5, -10, -5));
    assertEquals(-10, boundAspectRatio.equalize().getLeft(), 0.001);
  }

  @Test
  public void equalize_TallerThanWide_RightAdjusted() {
    AspectRatioEqualizer boundAspectRatio = new AspectRatioEqualizer(new Bounds(10, 5, -10, -5));
    assertEquals(10, boundAspectRatio.equalize().getRight(), 0.001);
  }

  @Test
  public void equalize_TallerThanWide_TopNotTouched() {
    AspectRatioEqualizer boundAspectRatio = new AspectRatioEqualizer(new Bounds(10, 5, -10, -5));
    assertEquals(10, boundAspectRatio.equalize().getTop(), 0.001);
  }

  @Test
  public void equalize_TallerThanWide_BottomNotTouched() {
    AspectRatioEqualizer boundAspectRatio = new AspectRatioEqualizer(new Bounds(10, 5, -10, -5));
    assertEquals(-10, boundAspectRatio.equalize().getBottom(), 0.001);
  }
}
