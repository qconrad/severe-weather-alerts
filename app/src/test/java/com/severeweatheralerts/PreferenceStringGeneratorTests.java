package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Preferences.PreferenceStringGenerator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PreferenceStringGeneratorTests {
  @Test
  public void getString_TorPostForLoc0_CorrectString() {
    PreferenceStringGenerator preferenceStringGenerator = new PreferenceStringGenerator(0, Alert.Type.POST, "Tornado Warning");
    assertEquals("0POSTTornado Warning", preferenceStringGenerator.getString());
  }

  @Test
  public void getString_TorPostForLoc1_CorrectString() {
    PreferenceStringGenerator preferenceStringGenerator = new PreferenceStringGenerator(1, Alert.Type.POST, "Tornado Warning");
    assertEquals("1POSTTornado Warning", preferenceStringGenerator.getString());
  }

  @Test
  public void getString_TorUpdateForLoc1_CorrectString() {
    PreferenceStringGenerator preferenceStringGenerator = new PreferenceStringGenerator(1, Alert.Type.UPDATE, "Tornado Warning");
    assertEquals("1UPDATETornado Warning", preferenceStringGenerator.getString());
  }

  @Test
  public void getString_WindUpdateForLoc1_CorrectString() {
    PreferenceStringGenerator preferenceStringGenerator = new PreferenceStringGenerator(1, Alert.Type.UPDATE, "Wind Advisory");
    assertEquals("1UPDATEWind Advisory", preferenceStringGenerator.getString());
  }
}
