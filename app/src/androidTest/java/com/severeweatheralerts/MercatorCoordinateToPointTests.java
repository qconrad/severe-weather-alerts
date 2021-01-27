package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.Bounds;
import com.severeweatheralerts.Graphics.MercatorCoordinate;
import com.severeweatheralerts.Graphics.MercatorCoordinateToPointAdapter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MercatorCoordinateToPointTests {
  @Test
  public void getPoint_LeftOfFrameProvided_XPixelIs0() {
    Bounds bounds = new Bounds(-5, 5, 5, -5);
    MercatorCoordinateToPointAdapter mercatorCoordinateToPointAdapter = new MercatorCoordinateToPointAdapter(bounds, 512, 512);
    assertEquals(0, mercatorCoordinateToPointAdapter.getPoint(new MercatorCoordinate(-5, 0)).x);
  }

  @Test
  public void getPoint_RightOfFrameProvided_XPixelIsRightBound() {
    Bounds bounds = new Bounds(-5, 5, 5, -5);
    MercatorCoordinateToPointAdapter mercatorCoordinateToPointAdapter = new MercatorCoordinateToPointAdapter(bounds, 512, 512);
    assertEquals(512, mercatorCoordinateToPointAdapter.getPoint(new MercatorCoordinate(5, 0)).x);
  }

  @Test
  public void getPoint_MiddleOfXProvided_XIsHalfOfBound() {
    Bounds bounds = new Bounds(-5, 5, 5, -5);
    MercatorCoordinateToPointAdapter mercatorCoordinateToPointAdapter = new MercatorCoordinateToPointAdapter(bounds, 512, 512);
    assertEquals(256, mercatorCoordinateToPointAdapter.getPoint(new MercatorCoordinate(0, 0)).x);
  }

  @Test
  public void getPoint_RightOfFrameProvidedWithDifferentBound_XIsBound() {
    Bounds bounds = new Bounds(-5, 5, 5, -5);
    MercatorCoordinateToPointAdapter mercatorCoordinateToPointAdapter = new MercatorCoordinateToPointAdapter(bounds, 128, 512);
    assertEquals(64, mercatorCoordinateToPointAdapter.getPoint(new MercatorCoordinate(0, 0)).x);
  }

  @Test
  public void getPoint_MiddleIsNotZero_CorrectXReturned() {
    Bounds bounds = new Bounds(5, 10, -5, 0);
    MercatorCoordinateToPointAdapter mercatorCoordinateToPointAdapter = new MercatorCoordinateToPointAdapter(bounds, 512, 512);
    assertEquals(256, mercatorCoordinateToPointAdapter.getPoint(new MercatorCoordinate(5, 0)).x);
  }

  @Test
  public void getPoint_RightOfFrameProvided_CorrectXReturned() {
    Bounds bounds = new Bounds(5, 10, -5, 0);
    MercatorCoordinateToPointAdapter mercatorCoordinateToPointAdapter = new MercatorCoordinateToPointAdapter(bounds, 512, 512);
    assertEquals(512, mercatorCoordinateToPointAdapter.getPoint(new MercatorCoordinate(10, 0)).x);
  }

  @Test
  public void getPoint_QuarterWayOfLengthProvided_CorrectXReturned() {
    Bounds bounds = new Bounds(5, 16, -5, 0);
    MercatorCoordinateToPointAdapter mercatorCoordinateToPointAdapter = new MercatorCoordinateToPointAdapter(bounds, 512, 512);
    assertEquals(128, mercatorCoordinateToPointAdapter.getPoint(new MercatorCoordinate(4, 0)).x);
  }

  @Test
  public void getPoint_BottomOfFrameProvided_CorrectYReturned() {
    Bounds bounds = new Bounds(5, 0, -5, 0);
    MercatorCoordinateToPointAdapter mercatorCoordinateToPointAdapter = new MercatorCoordinateToPointAdapter(bounds, 512, 512);
    assertEquals(512, mercatorCoordinateToPointAdapter.getPoint(new MercatorCoordinate(0, 5)).y);
  }

  @Test
  public void getPoint_DifferentHeightProvided_CorrectYReturned() {
    Bounds bounds = new Bounds(5, 0, -5, 0);
    MercatorCoordinateToPointAdapter mercatorCoordinateToPointAdapter = new MercatorCoordinateToPointAdapter(bounds, 512, 256);
    assertEquals(256, mercatorCoordinateToPointAdapter.getPoint(new MercatorCoordinate(0, 5)).y);
  }

  @Test
  public void getPoint_BothNegative_CorrectYReturned() {
    Bounds bounds = new Bounds(-10, 0, -20, 0);
    MercatorCoordinateToPointAdapter mercatorCoordinateToPointAdapter = new MercatorCoordinateToPointAdapter(bounds, 512, 256);
    assertEquals(128, mercatorCoordinateToPointAdapter.getPoint(new MercatorCoordinate(0, -15)).y);
  }
}
