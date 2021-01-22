package com.severeweatheralerts.Networking.FetchServices;

public interface FetchCallback {
  void success(String response);
  void error(String message);
}
