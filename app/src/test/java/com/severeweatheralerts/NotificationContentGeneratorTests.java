package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.Notifications.NotificationContentGenerator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NotificationContentGeneratorTests {
  @Test
  public void getContent_descriptionProvided_ContentIsDescription() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setDescription("This is a description.");
    NotificationContentGenerator notificationContentGenerator = new NotificationContentGenerator(defaultAlert);
    assertEquals("This is a description.", notificationContentGenerator.getContent());
  }

  @Test
  public void getContent_differentDescriptionProvided_ContentIsDescription() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setDescription("This is a different description.");
    NotificationContentGenerator notificationContentGenerator = new NotificationContentGenerator(defaultAlert);
    assertEquals("This is a different description.", notificationContentGenerator.getContent());
  }

  @Test
  public void getContent_LargeHeadlineProvided_HeadlineInContent() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setLargeHeadline("Big Event Expected Today");
    defaultAlert.setDescription("This is a different description.");
    NotificationContentGenerator notificationContentGenerator = new NotificationContentGenerator(defaultAlert);
    assertEquals("Big Event Expected Today\n\nThis is a different description.", notificationContentGenerator.getContent());
  }

  @Test
  public void getContent_SmallHeadlineProvided_HeadlineInContent() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setSmallHeadline("ACTIVE ALERT IN EFFECT UNTIL LATER");
    defaultAlert.setDescription("This is a different description.");
    NotificationContentGenerator notificationContentGenerator = new NotificationContentGenerator(defaultAlert);
    assertEquals("ACTIVE ALERT IN EFFECT UNTIL LATER\n\nThis is a different description.", notificationContentGenerator.getContent());
  }

  @Test
  public void getContent_SmallAndLargeHeadlineProvided_BothHeadlineInContent() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setLargeHeadline("Big Event Expected Today");
    defaultAlert.setSmallHeadline("ACTIVE ALERT IN EFFECT UNTIL LATER");
    defaultAlert.setDescription("This is a different description.");
    NotificationContentGenerator notificationContentGenerator = new NotificationContentGenerator(defaultAlert);
    assertEquals("Big Event Expected Today\n\nACTIVE ALERT IN EFFECT UNTIL LATER\n\nThis is a different description.", notificationContentGenerator.getContent());
  }

  @Test
  public void getContent_InstructionGiven_InstructionInContent() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setDescription("This is a different description.");
    defaultAlert.setInstruction("Take action now.");
    NotificationContentGenerator notificationContentGenerator = new NotificationContentGenerator(defaultAlert);
    assertEquals("This is a different description.\n\nTake action now.", notificationContentGenerator.getContent());
  }
}
