package com.severeweatheralerts.Networking.LocationPopulaters;

import com.severeweatheralerts.Alerts.Alert;

import java.util.ArrayList;

public interface PopulateCallback {
  void complete(ArrayList<Alert> alerts);
  void error(String message);
}
