package com.severeweatheralerts.Adapters;

import com.severeweatheralerts.Location.Location;

import java.util.ArrayList;

public class LocationToCoordinateListAdapter {
  private final ArrayList<Location> locations;

  public LocationToCoordinateListAdapter(ArrayList<Location> locations) {
    this.locations = locations;
  }

  public ArrayList<GCSCoordinate> getCoordinates() {
    ArrayList<GCSCoordinate> coordinates = new ArrayList<>();
    for (int i = 0; i < locations.size(); i++)
      coordinates.add(locations.get(0).getCoordinate());
    return coordinates;
  }
}
