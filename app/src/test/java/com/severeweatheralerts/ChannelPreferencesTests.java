package com.severeweatheralerts;

import com.severeweatheralerts.Preferences.ChannelPreferences;

import org.junit.Test;

import static com.severeweatheralerts.Alerts.Alert.Type.*;
import static com.severeweatheralerts.Preferences.Channel.*;
import static org.junit.Assert.assertEquals;

public class ChannelPreferencesTests {
  @Test
  public void getChannel_TornadoWarningPost_defaultOfExtremeReturn() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    assertEquals(EXTREME, channelPreferences.getChannel("Tornado Warning", POST));
  }

  @Test
  public void getChannel_TornadoWarningUpdate_defaultOfHigh() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    assertEquals(HIGH, channelPreferences.getChannel("Tornado Warning", UPDATE));
  }

  @Test
  public void getChannel_TornadoWarningCancel_defaultOfHigh() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    assertEquals(HIGH, channelPreferences.getChannel("Tornado Warning", CANCEL));
  }

  @Test
  public void getChannel_InvalidAlert_defaultOfMedium() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    assertEquals(MEDIUM, channelPreferences.getChannel("Random Invalid Alert Name", CANCEL));
  }

  @Test
  public void setChannel_tornadoPostSetToLow_returnsLow() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Tornado Warning", POST, LOW);
    assertEquals(LOW, channelPreferences.getChannel("Tornado Warning", POST));
  }

  @Test
  public void setChannel_tornadoPostSetToMedium_returnsMedium() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Tornado Warning", POST, MEDIUM);
    assertEquals(MEDIUM, channelPreferences.getChannel("Tornado Warning", POST));
  }

  @Test
  public void setChannel_tornadoPostSetToHigh_returnsHigh() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Tornado Warning", POST, HIGH);
    assertEquals(HIGH, channelPreferences.getChannel("Tornado Warning", POST));
  }

  @Test
  public void setChannel_windPostSetToHigh_TornadoWarningUnaffected() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Wind Advisory", POST, HIGH);
    assertEquals(EXTREME, channelPreferences.getChannel("Tornado Warning", POST));
  }

  @Test
  public void setChannel_windPostSetToLow_TornadoWarningUnaffected() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Wind Advisory", POST, LOW);
    assertEquals(EXTREME, channelPreferences.getChannel("Tornado Warning", POST));
  }

  @Test
  public void setChannel_windPostSetToMedium_TornadoWarningUnaffected() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Wind Advisory", POST, MEDIUM);
    assertEquals(EXTREME, channelPreferences.getChannel("Tornado Warning", POST));
  }

  @Test
  public void setChannel_windPostSetToLow_ReturnsLow() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Wind Advisory", POST, LOW);
    assertEquals(LOW, channelPreferences.getChannel("Wind Advisory", POST));
  }

  @Test
  public void setChannel_windPostSetToMedium_ReturnsMedium() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Wind Advisory", POST, MEDIUM);
    assertEquals(MEDIUM, channelPreferences.getChannel("Wind Advisory", POST));
  }

  @Test
  public void setChannel_hydroPostSetToHigh_ReturnsHigh() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Hydrologic Outlook", POST, HIGH);
    assertEquals(HIGH, channelPreferences.getChannel("Hydrologic Outlook", POST));
  }

  @Test
  public void setChannel_tornadoUpdateSetLow_PostUnaffected() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Tornado Warning", UPDATE, LOW);
    assertEquals(EXTREME, channelPreferences.getChannel("Tornado Warning", POST));
  }

  @Test
  public void setChannel_tornadoUpdateSetLow_ReturnsLow() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Tornado Warning", UPDATE, LOW);
    assertEquals(LOW, channelPreferences.getChannel("Tornado Warning", UPDATE));
  }

  @Test
  public void setChannel_windSetToExtreme_returnsExtreme() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Wind Advisory", POST, EXTREME);
    assertEquals(EXTREME, channelPreferences.getChannel("Wind Advisory", POST));
  }

  @Test
  public void setChannel_tornadoSetToNone_returnsNone() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Tornado Warning", POST, NONE);
    assertEquals(NONE, channelPreferences.getChannel("Tornado Warning", POST));
  }

  @Test
  public void getUserMap_Size0() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    assertEquals(0, channelPreferences.getUserMap().size());
  }

  @Test
  public void getUserMap_Size1() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Tornado Warning", POST, NONE);
    assertEquals(1, channelPreferences.getUserMap().size());
  }

  @Test
  public void getUserMap_setToDefault_Size0() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Tornado Warning", POST, EXTREME);
    assertEquals(0, channelPreferences.getUserMap().size());
  }

  @Test
  public void getUserMap_setToHighThenDefaultDefault_Size0() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Tornado Warning", POST, HIGH);
    channelPreferences.setChannel("Tornado Warning", POST, EXTREME);
    assertEquals(0, channelPreferences.getUserMap().size());
  }
}
