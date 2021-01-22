package com.severeweatheralerts.Networking;

public interface FetchCallback {
  void success(String response);
  void error(String message);
}
