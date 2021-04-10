package com.severeweatheralerts;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.UserSync.JSONLocationString;
import com.severeweatheralerts.UserSync.UserSyncJSONGenerator;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UserSyncJSONGeneratorTests {
  @Test
  public void getString_TokenAndBuildProvided_TokenAndBuildInString() {
    String token = "0123456789abc";
    UserSyncJSONGenerator userSyncJSONGenerator = new UserSyncJSONGenerator(token);
    String output = userSyncJSONGenerator.getLocationsString(new JSONLocationString(new ArrayList<GCSCoordinate>()).getString());
    assertEquals("{\"token\":\"0123456789abc\",\"locations\":[[]]}", output);
  }
}
