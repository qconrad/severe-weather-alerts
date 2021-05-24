package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.DiagonalOffset;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DiagonalOffsetTests {
  @Test
  public void eastAngle_YOffsetZero() {
    assertEquals(0, new DiagonalOffset(1, 270).getY(), 0.001);
  }

  @Test
  public void southAngle_YOffsetRadius() {
    assertEquals(-1, new DiagonalOffset(1, 0).getY(), 0.001);
  }

  @Test
  public void southAngle_YOffsetIsDifferentRadius() {
    assertEquals(-2, new DiagonalOffset(2, 0).getY(), 0.001);
  }

  @Test
  public void westAngle_YOffsetIsOne() {
    assertEquals(1, new DiagonalOffset(1, 180).getY(), 0.001);
  }

  @Test
  public void northWestAngle_YOffsetIsHalfRadius() {
    assertEquals(0.5, new DiagonalOffset(1, 120).getY(), 0.001);
  }

  @Test
  public void northEastAngle_XOffsetIsHalfRadius() {
    assertEquals(-0.5, new DiagonalOffset(1, 150).getX(), 0.001);
  }

  @Test
  public void eastAngle_XOffsetIsZero() {
    assertEquals(1, new DiagonalOffset(1, 270).getX(), 0.001);
  }
}
