package com.severeweatheralerts;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Graphics.GCSToMercatorCoordinateAdapter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MercatorCoordinateAdapterTests {
  @Test
  public void getCoordinate_0LatitudeGiven_YIs0() {
    GCSToMercatorCoordinateAdapter GCSToMercatorCoordinateAdapter = new GCSToMercatorCoordinateAdapter(new GCSCoordinate(0.0, 0.0));
    assertEquals(0.0, GCSToMercatorCoordinateAdapter.getCoordinate().getY(), 0.001);
  }

  @Test
  public void getCoordinate_0LongitudeGiven_XIs0() {
    GCSToMercatorCoordinateAdapter GCSToMercatorCoordinateAdapter = new GCSToMercatorCoordinateAdapter(new GCSCoordinate(0.0, 0.0));
    assertEquals(0.0, GCSToMercatorCoordinateAdapter.getCoordinate().getX(), 0.001);
  }

  @Test
  public void getCoordinate_1LatitudeGiven_YisCorrect() {
    GCSToMercatorCoordinateAdapter GCSToMercatorCoordinateAdapter = new GCSToMercatorCoordinateAdapter(new GCSCoordinate(1.0, 0.0));
    assertEquals(111325.14, GCSToMercatorCoordinateAdapter.getCoordinate().getY(), 0.01);
  }

  @Test
  public void getCoordinate_1LongtiudeGiven_XisCorrect() {
    GCSToMercatorCoordinateAdapter GCSToMercatorCoordinateAdapter = new GCSToMercatorCoordinateAdapter(new GCSCoordinate(0.0, 1.0));
    assertEquals(111319.49, GCSToMercatorCoordinateAdapter.getCoordinate().getX(), 0.01);
  }
}
