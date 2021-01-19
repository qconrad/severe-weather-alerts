package com.severeweatheralerts.UserSync;

public class UserSyncJSONGenerator {
  private final String token;
  private final int buildNum;

  public UserSyncJSONGenerator(String token, int buildNum) {
    this.token = token;
    this.buildNum = buildNum;
  }

  public String getLocationsString(String locationJSON) {
    return "{\"" + token + "\":{\"build\":"+ buildNum + ",\"locations\":" + locationJSON + "}}";
  }
}
