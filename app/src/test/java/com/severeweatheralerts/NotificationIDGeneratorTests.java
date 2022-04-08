package com.severeweatheralerts;

import static org.junit.Assert.assertEquals;

import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.Notifications.NotificationIdGenerator;

import org.junit.Test;

public class NotificationIDGeneratorTests {
  @Test
  public void testThatIdIsGenerated() {
    DefaultAlert alert = new DefaultAlert();
    alert.setName("DefaultAlert");
    NotificationIdGenerator generator = new NotificationIdGenerator(alert, 0);
    assertEquals("DefaultAlert0", generator.generateId());
  }

  @Test
  public void testWithLocation() {
    DefaultAlert alert = new DefaultAlert();
    alert.setName("DefaultAlert");
    NotificationIdGenerator generator = new NotificationIdGenerator(alert, 1);
    assertEquals("DefaultAlert1", generator.generateId());
  }

  @Test
  public void testWithDifferentAlert() {
    DefaultAlert alert = new DefaultAlert();
    alert.setName("TornadoWarning");
    NotificationIdGenerator generator = new NotificationIdGenerator(alert, 1);
    assertEquals("TornadoWarning1", generator.generateId());
  }

  @Test
  public void testWithSpacesInName_SpacesRemoved() {
    DefaultAlert alert = new DefaultAlert();
    alert.setName("Tornado Warning");
    NotificationIdGenerator generator = new NotificationIdGenerator(alert, 1);
    assertEquals("TornadoWarning1", generator.generateId());
  }
}
