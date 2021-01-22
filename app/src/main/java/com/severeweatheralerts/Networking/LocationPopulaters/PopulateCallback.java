package com.severeweatheralerts.Networking.LocationPopulaters;

public interface PopulateCallback {
  void complete();
  void error(String message);
}
