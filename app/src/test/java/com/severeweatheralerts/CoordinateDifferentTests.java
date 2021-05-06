package com.severeweatheralerts;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Location.CoordinateDifference;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class CoordinateDifferentTests {
  @Test
  public void coordinateTheSame_isDifferentReturnsFalse() {
    GCSCoordinate lastCoordinate = new GCSCoordinate(41.0, -80.0);
    GCSCoordinate currentCoordinate = new GCSCoordinate(41.0, -80.0);
    assertFalse(new CoordinateDifference(lastCoordinate, currentCoordinate).isDifferent(0.001));
  }

  @Test
  public void latDifferent_isDifferentReturnsTrue() {
    GCSCoordinate lastCoordinate = new GCSCoordinate(45.0, -80.0);
    GCSCoordinate currentCoordinate = new GCSCoordinate(41.0, -80.0);
    assertTrue(new CoordinateDifference(lastCoordinate, currentCoordinate).isDifferent(0.001));
  }

  @Test
  public void latDifferent_DifferentInput_isDifferentReturnsTrue() {
    GCSCoordinate lastCoordinate = new GCSCoordinate(46.0, -80.0);
    GCSCoordinate currentCoordinate = new GCSCoordinate(41.0, -80.0);
    assertTrue(new CoordinateDifference(lastCoordinate, currentCoordinate).isDifferent(0.001));
  }

  @Test
  public void lonDifferent_isDifferentReturnsTrue() {
    GCSCoordinate lastCoordinate = new GCSCoordinate(41.0, -85.0);
    GCSCoordinate currentCoordinate = new GCSCoordinate(41.0, -80.0);
    assertTrue(new CoordinateDifference(lastCoordinate, currentCoordinate).isDifferent(0.001));
  }

  @Test
  public void lonDifferent_DifferentInput_isDifferentReturnsTrue() {
    GCSCoordinate lastCoordinate = new GCSCoordinate(41.0, -86.0);
    GCSCoordinate currentCoordinate = new GCSCoordinate(41.0, -80.0);
    assertTrue(new CoordinateDifference(lastCoordinate, currentCoordinate).isDifferent(0.001));
  }

  @Test
  public void latDifferent_WithinRange_isDifferentReturnsFalse() {
    GCSCoordinate lastCoordinate = new GCSCoordinate(41.5, -80.0);
    GCSCoordinate currentCoordinate = new GCSCoordinate(41.0, -80.0);
    assertFalse(new CoordinateDifference(lastCoordinate, currentCoordinate).isDifferent(1.0));
  }

  @Test
  public void latFlipped_OutOfRange_isDifferentReturnsTrue() {
    GCSCoordinate lastCoordinate = new GCSCoordinate(41.0, -80.0);
    GCSCoordinate currentCoordinate = new GCSCoordinate(43.0, -80.0);
    assertTrue(new CoordinateDifference(lastCoordinate, currentCoordinate).isDifferent(1.0));
  }

  @Test
  public void lonDifferent_WithinRange_isDifferentReturnsFalse() {
    GCSCoordinate lastCoordinate = new GCSCoordinate(41.0, -80.5);
    GCSCoordinate currentCoordinate = new GCSCoordinate(41.0, -80.0);
    assertFalse(new CoordinateDifference(lastCoordinate, currentCoordinate).isDifferent(1.0));
  }

  @Test
  public void lonFlipped_OutOfRange_isDifferentReturnsFalse() {
    GCSCoordinate lastCoordinate = new GCSCoordinate(41.0, -80.0);
    GCSCoordinate currentCoordinate = new GCSCoordinate(41.0, -85.0);
    assertTrue(new CoordinateDifference(lastCoordinate, currentCoordinate).isDifferent(1.0));
  }
}
