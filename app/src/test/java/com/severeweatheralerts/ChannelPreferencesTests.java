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
    assertEquals(EXTREME, channelPreferences.getChannel(0, POST, "Tornado Warning"));
  }

  @Test
  public void getChannel_TornadoWarningUpdate_defaultOfHigh() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    assertEquals(HIGH, channelPreferences.getChannel(0, UPDATE, "Tornado Warning"));
  }

  @Test
  public void getChannel_TornadoWarningCancel_defaultOfHigh() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    assertEquals(HIGH, channelPreferences.getChannel(0, CANCEL, "Tornado Warning"));
  }

  @Test
  public void getChannel_InvalidAlert_defaultOfMedium() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    assertEquals(MEDIUM, channelPreferences.getChannel(0, CANCEL, "Random Invalid Alert Name"));
  }

  @Test
  public void setChannel_tornadoPostSetToLow_returnsLow() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel(0, POST, "Tornado Warning", LOW);
    assertEquals(LOW, channelPreferences.getChannel(0, POST, "Tornado Warning"));
  }

  @Test
  public void setChannel_tornadoPostSetToMedium_returnsMedium() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel(0, POST, "Tornado Warning", MEDIUM);
    assertEquals(MEDIUM, channelPreferences.getChannel(0, POST, "Tornado Warning"));
  }

  @Test
  public void setChannel_tornadoPostSetToHigh_returnsHigh() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel(0, POST, "Tornado Warning", HIGH);
    assertEquals(HIGH, channelPreferences.getChannel(0, POST, "Tornado Warning"));
  }

  @Test
  public void setChannel_windPostSetToHigh_TornadoWarningUnaffected() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel(0, POST, "Wind Advisory", HIGH);
    assertEquals(EXTREME, channelPreferences.getChannel(0, POST, "Tornado Warning"));
  }

  @Test
  public void setChannel_windPostSetToLow_TornadoWarningUnaffected() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel(0, POST, "Wind Advisory", LOW);
    assertEquals(EXTREME, channelPreferences.getChannel(0, POST, "Tornado Warning"));
  }

  @Test
  public void setChannel_windPostSetToMedium_TornadoWarningUnaffected() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel(0, POST, "Wind Advisory", MEDIUM);
    assertEquals(EXTREME, channelPreferences.getChannel(0, POST, "Tornado Warning"));
  }

  @Test
  public void setChannel_windPostSetToLow_ReturnsLow() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel(0, POST, "Wind Advisory", LOW);
    assertEquals(LOW, channelPreferences.getChannel(0, POST, "Wind Advisory"));
  }

  @Test
  public void setChannel_windPostSetToMedium_ReturnsMedium() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel(0, POST, "Wind Advisory", MEDIUM);
    assertEquals(MEDIUM, channelPreferences.getChannel(0, POST, "Wind Advisory"));
  }

  @Test
  public void setChannel_hydroPostSetToHigh_ReturnsHigh() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel(0, POST, "Hydrologic Outlook", HIGH);
    assertEquals(HIGH, channelPreferences.getChannel(0, POST, "Hydrologic Outlook"));
  }

  @Test
  public void setChannel_tornadoUpdateSetLow_PostUnaffected() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel(0, UPDATE, "Tornado Warning", LOW);
    assertEquals(EXTREME, channelPreferences.getChannel(0, POST, "Tornado Warning"));
  }

  @Test
  public void setChannel_tornadoUpdateSetLow_ReturnsLow() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel(0, UPDATE, "Tornado Warning", LOW);
    assertEquals(LOW, channelPreferences.getChannel(0, UPDATE, "Tornado Warning"));
  }

  @Test
  public void setChannel_location1Set_location0Unchanged() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel(1, POST, "Tornado Warning", LOW);
    assertEquals(EXTREME, channelPreferences.getChannel(0, POST, "Tornado Warning"));
  }

  @Test
  public void setChannel_location1Set_returnsLow() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel(1, POST, "Tornado Warning", LOW);
    assertEquals(LOW, channelPreferences.getChannel(1, POST, "Tornado Warning"));
  }

  @Test
  public void setChannel_windSetToExtreme_returnsExtreme() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel(0, POST, "Wind Advisory", EXTREME);
    assertEquals(EXTREME, channelPreferences.getChannel(0, POST, "Wind Advisory"));
  }

  @Test
  public void setChannel_tornadoSetToNone_returnsNone() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel(0, POST, "Tornado Warning", NONE);
    assertEquals(NONE, channelPreferences.getChannel(0, POST, "Tornado Warning"));
  }

  @Test
  public void getUserMap_Size0() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    assertEquals(0, channelPreferences.getUserMap().size());
  }

  @Test
  public void getUserMap_Size1() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel(0, POST, "Tornado Warning", NONE);
    assertEquals(1, channelPreferences.getUserMap().size());
  }

  @Test
  public void getUserMap_setToDefault_Size0() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel(0, POST, "Tornado Warning", EXTREME);
    assertEquals(0, channelPreferences.getUserMap().size());
  }

  @Test
  public void getUserMap_setToHighThenDefaultDefault_Size0() {
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel(0, POST, "Tornado Warning", HIGH);
    channelPreferences.setChannel(0, POST, "Tornado Warning", EXTREME);
    assertEquals(0, channelPreferences.getUserMap().size());
  }
}
