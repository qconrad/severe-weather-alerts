package com.severeweatheralerts;

import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.UserSync.UserSyncJSONGenerator;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UserSyncJSONGeneratorTests {
  @Test
  public void getString_TokenAndLocationsProvided_ValidJSONReturned() {
    String token = "0123456789abc";
    UserSyncJSONGenerator userSyncJSONGenerator = new UserSyncJSONGenerator(token);
    String output = userSyncJSONGenerator.getLocationsString(new JSONLocationString(new ArrayList<Location>()).getString());
    assertEquals("{\"0123456789abc\":{\"locations\":[[]]}}", output);
  }
}
