package com.severeweatheralerts.Location;

import java.util.ArrayList;

public interface LocationDatabase {
  ArrayList<Location> get();
  void set(ArrayList<Location> locations);
}
