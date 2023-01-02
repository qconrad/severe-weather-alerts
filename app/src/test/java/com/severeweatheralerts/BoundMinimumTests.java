package com.severeweatheralerts;

import static junit.framework.TestCase.assertEquals;

import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.Tools.BoundMinimum;

import org.junit.Test;

// Expands the size of a bounds object to a minimum size if it is smaller than the minimum size
public class BoundMinimumTests {

  // minimum size equals bounds size, returns bounds
  @Test
  public void getBounds_BoundsNotSmallerThanMinimum_ReturnsBounds() {
    Bounds bounds = new Bounds(10, 10, 0, 0);
    int minimumSize = 10;
    assertEquals(10.0, new BoundMinimum(bounds, minimumSize).getBounds().getHeight());
    assertEquals(10.0, new BoundMinimum(bounds, minimumSize).getBounds().getWidth());
  }

  // If width is smaller than minimum size, the bounds are expanded to the minimum size
  @Test
  public void getBounds_VerticalSizeSmallerThanMinimum_ExpandsProportionally() {
    Bounds bounds = new Bounds(1, 5, -1, -5);
    int minimumSize = 20;
    assertEquals(100.0, new BoundMinimum(bounds, minimumSize).getBounds().getWidth());
  }

  // Vertical size is expanded proportionally to the horizontal size
  @Test
  public void getBounds_VerticalSizeSmallerThanMinimum_ExpandsProportionallyToHorizontalSize() {
    Bounds bounds = new Bounds(1, 5, -1, -5);
    int minimumSize = 20;
    assertEquals(20.0, new BoundMinimum(bounds, minimumSize).getBounds().getHeight());
  }

  // Width is expanded proportionally to the vertical size
  @Test
  public void getBounds_HorizontalSizeSmallerThanMinimum_ExpandsProportionallyToVerticalSize() {
    Bounds bounds = new Bounds(10, 5, -10, -5);
    int minimumSize = 20;
    assertEquals(20.0, new BoundMinimum(bounds, minimumSize).getBounds().getWidth());
  }

  // Both width and height are above the minimum size
  // The bounds are not changed
  @Test
  public void getBounds_BoundsAboveMinimumSize_ReturnsBounds() {
    Bounds bounds = new Bounds(10, 10, -10, -10);
    int minimumSize = 5;
    assertEquals(20.0, new BoundMinimum(bounds, minimumSize).getBounds().getWidth());
    assertEquals(20.0, new BoundMinimum(bounds, minimumSize).getBounds().getHeight());
  }

  // Width is longer than height
  // Height is expanded to the minimum size
  @Test
  public void getBounds_WidthLongerThanHeight_ExpandsHeightToMinimumSize() {
    Bounds bounds = new Bounds(10, 20, -10, -20);
    int minimumSize = 30;
    assertEquals(30.0, new BoundMinimum(bounds, minimumSize).getBounds().getHeight());
  }

  // Both width and height are below the minimum size
  // Width is longer than height
  // Height (smaller dimension) is expanded to the minimum size
  @Test
  public void getBounds_WidthLongerThanHeight_ExpandsHeightToMinimumSize2() {
    Bounds bounds = new Bounds(10, 20, -10, -20);
    int minimumSize = 50;
    assertEquals(50.0, new BoundMinimum(bounds, minimumSize).getBounds().getHeight());
  }

  // Aspect ratio is 1:1 both scaled
  @Test
  public void getBounds_AspectRatio1To1BothScaled_ReturnsBounds() {
    Bounds bounds = new Bounds(10, 10, -10, -10);
    int minimumSize = 40;
    assertEquals(40.0, new BoundMinimum(bounds, minimumSize).getBounds().getWidth());
    assertEquals(40.0, new BoundMinimum(bounds, minimumSize).getBounds().getHeight());
  }

  // Both bounds are above minimum size
  // Aspect ratio is not 1:1
  @Test
  public void getBounds_AspectRatioNot1To1BothScaled_ReturnsBounds() {
    Bounds bounds = new Bounds(20, 10, -20, -10);
    int minimumSize = 0;
    assertEquals(20.0, new BoundMinimum(bounds, minimumSize).getBounds().getWidth());
    assertEquals(40.0, new BoundMinimum(bounds, minimumSize).getBounds().getHeight());
  }
}
