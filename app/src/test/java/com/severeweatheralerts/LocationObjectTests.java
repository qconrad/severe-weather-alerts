package com.severeweatheralerts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.Preferences.ChannelPreferences;

import org.junit.Test;

import java.util.ArrayList;

public class LocationObjectTests {
  private Location makeLocation() {
    return new Location();
  }

  private Alert makeAlert() {
    return new DefaultAlert();
  }

  @Test
  public void createLocation_noAlertsAreAdded_noAlertsReturned() {
    Location loc = makeLocation();
    int size = loc.getAlerts().size();
    assertEquals(0, size);
  }

  @Test
  public void addAlert_alertCountIsOne_ThereIsOneAlertReturned() {
    Location loc = makeLocation();
    loc.addAlert(makeAlert());
    int size = loc.getAlerts().size();
    assertEquals(1, size);
  }

  @Test
  public void setAlert_twoAlertsAreAdded_TwoAlertsReturned() {
    Location loc = makeLocation();
    ArrayList<Alert> alertList = new ArrayList<>();
    alertList.add(makeAlert());
    alertList.add(makeAlert());
    loc.setAlerts(alertList);
    int size = loc.getAlerts().size();
    assertEquals(2, size);
  }

  @Test
  public void getLat_LatProvided_CorrectReturn() {
    Location loc = makeLocation();
    loc.setCoordinate(new GCSCoordinate(41.0, 0.0));
    assertEquals(41.0, loc.getCoordinate().getLat(), 0.001);
  }

  @Test
  public void getLong_LongProvided_CorrectReturn() {
    Location loc = makeLocation();
    loc.setCoordinate(new GCSCoordinate(41.0, -87.0));
    assertEquals(-87.0, loc.getCoordinate().getLong(), 0.001);
  }

  @Test
  public void getLong_DifferentLongProvided_CorrectReturn() {
    Location loc = makeLocation();
    loc.setCoordinate(new GCSCoordinate(41.0, -88.0));
    assertEquals(-88.0, loc.getCoordinate().getLong(), 0.001);
  }

  @Test
  public void getName_ReturnsSetName() {
    Location loc = makeLocation();
    loc.setName("Name");
    assertEquals("Name", loc.getName());
  }

  @Test
  public void getName_ReturnsDifferentSetName() {
    Location loc = makeLocation();
    loc.setName("Different Name");
    assertEquals("Different Name", loc.getName());
  }

  @Test
  public void nameConstructor() {
    Location loc = new Location("Even More Different Name");
    assertEquals("Even More Different Name", loc.getName());
  }

  @Test
  public void differentNameConstructor() {
    Location loc = new Location("Different Name");
    assertEquals("Different Name", loc.getName());
  }

  @Test
  public void channelPreferencesNull() {
    Location loc = new Location();
    assertNull(loc.getChannelPreferences());
  }

  @Test
  public void preferencesGiven_channelPreferencesReturned() {
    Location loc = new Location();
    ChannelPreferences channelPreferences = new ChannelPreferences();
    channelPreferences.setChannel("Tornado Warning", Alert.Type.POST, Channel.HIGH);
    loc.setChannelPreferences(channelPreferences);
    assertEquals(Channel.HIGH, loc.getChannelPreferences().getChannel("Tornado Warning", Alert.Type.POST));
  }

  @Test
  public void nameConstructorUsed_AlertsNotNull() {
    Location loc = new Location("Name");
    assertNotNull(loc.getAlerts());
  }

  @Test
  public void notificationsEnabledByDefault() {
    Location loc = new Location("Name");
    assertTrue(loc.isNotifying());
  }

  @Test
  public void disableNotifications_isNotifyingFalse() {
    Location loc = new Location("Name");
    loc.setNotify(false);
    assertFalse(loc.isNotifying());
  }
}
