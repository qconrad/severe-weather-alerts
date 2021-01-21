package com.severeweatheralerts;

import com.severeweatheralerts.Adapters.GCSCoordinate;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MercatorCoordinateAdapterTests {
  @Test
  public void getCoordinate_0LatitudeGiven_YIs0() {
    MercatorCoordinateAdapter mercatorCoordinateAdapter = new MercatorCoordinateAdapter(new GCSCoordinate(0.0, 0.0));
    assertEquals(0.0, mercatorCoordinateAdapter.getCoordinate().getY(), 0.001);
  }

  @Test
  public void getCoordinate_0LongitudeGiven_XIs0() {
    MercatorCoordinateAdapter mercatorCoordinateAdapter = new MercatorCoordinateAdapter(new GCSCoordinate(0.0, 0.0));
    assertEquals(0.0, mercatorCoordinateAdapter.getCoordinate().getX(), 0.001);
  }

  @Test
  public void getCoordinate_1LatitudeGiven_YisCorrect() {
    MercatorCoordinateAdapter mercatorCoordinateAdapter = new MercatorCoordinateAdapter(new GCSCoordinate(1.0, 0.0));
    assertEquals(111325.14, mercatorCoordinateAdapter.getCoordinate().getY(), 0.01);
  }

  @Test
  public void getCoordinate_1LongtiudeGiven_XisCorrect() {
    MercatorCoordinateAdapter mercatorCoordinateAdapter = new MercatorCoordinateAdapter(new GCSCoordinate(0.0, 1.0));
    assertEquals(111319.49, mercatorCoordinateAdapter.getCoordinate().getX(), 0.01);
  }
}
