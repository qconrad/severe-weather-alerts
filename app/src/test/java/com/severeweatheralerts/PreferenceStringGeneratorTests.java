package com.severeweatheralerts;

import com.severeweatheralerts.Preferences.PreferenceStringGenerator;

import org.junit.Test;

import static com.severeweatheralerts.Alerts.Alert.Type.*;
import static org.junit.Assert.assertEquals;

public class PreferenceStringGeneratorTests {
  @Test
  public void getString_TorPostForLoc0_CorrectString() {
    PreferenceStringGenerator preferenceStringGenerator = new PreferenceStringGenerator("Tornado Warning", POST);
    assertEquals("POSTTornado Warning", preferenceStringGenerator.getString());
  }

  @Test
  public void getString_TorPostForLoc1_CorrectString() {
    PreferenceStringGenerator preferenceStringGenerator = new PreferenceStringGenerator("Tornado Warning", POST);
    assertEquals("POSTTornado Warning", preferenceStringGenerator.getString());
  }

  @Test
  public void getString_TorUpdateForLoc1_CorrectString() {
    PreferenceStringGenerator preferenceStringGenerator = new PreferenceStringGenerator("Tornado Warning", UPDATE);
    assertEquals("UPDATETornado Warning", preferenceStringGenerator.getString());
  }

  @Test
  public void getString_WindUpdateForLoc1_CorrectString() {
    PreferenceStringGenerator preferenceStringGenerator = new PreferenceStringGenerator("Wind Advisory", UPDATE);
    assertEquals("UPDATEWind Advisory", preferenceStringGenerator.getString());
  }
}
