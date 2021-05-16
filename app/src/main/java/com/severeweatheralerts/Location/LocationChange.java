package com.severeweatheralerts.Location;

import android.location.Location;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Constants;

public class LocationChange {
  private final GCSCoordinate currentCoordinate;
  private final GCSCoordinate newCoordinate;

  public LocationChange(GCSCoordinate currentCoordinate, Location newCoordinate) {
    this.currentCoordinate = currentCoordinate;
    this.newCoordinate = new GCSCoordinate(newCoordinate.getLatitude(), newCoordinate.getLongitude());
  }

  public boolean hasChanged() {
    return new CoordinateDifference(currentCoordinate, newCoordinate).isDifferent(Constants.LOCATION_CHANGE_MARGIN);
  }
}
