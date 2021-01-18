package com.severeweatheralerts.UserSync;

public class UserSyncJSONGenerator {
  private final String token;

  public UserSyncJSONGenerator(String token) {
    this.token = token;
  }

  public String getLocationsString(String locationJSON) {
    return "{\"" + token + "\":{\"locations\":" + locationJSON + "}}";
  }
}
