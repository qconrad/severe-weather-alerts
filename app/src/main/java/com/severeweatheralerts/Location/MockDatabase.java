package com.severeweatheralerts.Location;

import java.util.ArrayList;

public class MockDatabase implements LocationDatabase {
  private ArrayList<Location> locations;

  public MockDatabase(ArrayList<Location> locations) {
    this.locations = locations;
  }

  public MockDatabase() {
    locations = new ArrayList<>();
  }

  @Override
  public ArrayList<Location> get() {
    return new ArrayList<>(locations);
  }

  @Override
  public void set(ArrayList<Location> locations) {
    this.locations = new ArrayList<>(locations);
  }
}
