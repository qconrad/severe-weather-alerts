package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.Notifications.NotificationContentGenerator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class NotificationContentGeneratorTests {
  @Test
  public void getLongText_descriptionProvided_ContentIsDescription() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setDescription("This is a description.");
    NotificationContentGenerator notificationContentGenerator = new NotificationContentGenerator(defaultAlert);
    assertEquals("This is a description.", notificationContentGenerator.getLongText());
  }

  @Test
  public void getLongText_differentDescriptionProvided_ContentIsDescription() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setDescription("This is a different description.");
    NotificationContentGenerator notificationContentGenerator = new NotificationContentGenerator(defaultAlert);
    assertEquals("This is a different description.", notificationContentGenerator.getLongText());
  }

  @Test
  public void getLongText_LargeHeadlineProvided_HeadlineInContent() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setLargeHeadline("Big Event Expected Today");
    defaultAlert.setDescription("This is a different description.");
    NotificationContentGenerator notificationContentGenerator = new NotificationContentGenerator(defaultAlert);
    assertEquals("Big Event Expected Today\n\nThis is a different description.", notificationContentGenerator.getLongText());
  }

  @Test
  public void getLongText_SmallHeadlineProvided_HeadlineInContent() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setSmallHeadline("ACTIVE ALERT IN EFFECT UNTIL LATER");
    defaultAlert.setDescription("This is a different description.");
    NotificationContentGenerator notificationContentGenerator = new NotificationContentGenerator(defaultAlert);
    assertEquals("ACTIVE ALERT IN EFFECT UNTIL LATER\n\nThis is a different description.", notificationContentGenerator.getLongText());
  }

  @Test
  public void getLongText_SmallAndLargeHeadlineProvided_BothHeadlineInContent() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setLargeHeadline("Big Event Expected Today");
    defaultAlert.setSmallHeadline("ACTIVE ALERT IN EFFECT UNTIL LATER");
    defaultAlert.setDescription("This is a different description.");
    NotificationContentGenerator notificationContentGenerator = new NotificationContentGenerator(defaultAlert);
    assertEquals("Big Event Expected Today\n\nACTIVE ALERT IN EFFECT UNTIL LATER\n\nThis is a different description.", notificationContentGenerator.getLongText());
  }

  @Test
  public void getLongText_InstructionGiven_InstructionInContent() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setDescription("This is a different description.");
    defaultAlert.setInstruction("Take action now.");
    NotificationContentGenerator notificationContentGenerator = new NotificationContentGenerator(defaultAlert);
    assertEquals("This is a different description.\n\nTake action now.", notificationContentGenerator.getLongText());
  }

  @Test
  public void getLongText_onlyDescriptionProvided_ShortTextNull() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setDescription("This is a description.");
    NotificationContentGenerator notificationContentGenerator = new NotificationContentGenerator(defaultAlert);
    assertNull(notificationContentGenerator.getShortText());
  }

  @Test
  public void getLongText_largeHeadlineProvided_ShortTextIsThatHeadline() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setLargeHeadline("Large headline");
    NotificationContentGenerator notificationContentGenerator = new NotificationContentGenerator(defaultAlert);
    assertEquals("Large headline", notificationContentGenerator.getShortText());
  }

  @Test
  public void getLongText_smallHeadlineProvided_ShortTextIsThatHeadline() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setSmallHeadline("Small headline");
    NotificationContentGenerator notificationContentGenerator = new NotificationContentGenerator(defaultAlert);
    assertEquals("Small headline", notificationContentGenerator.getShortText());
  }

  @Test
  public void getLongText_BothHeadlinesProvided_ShortTextIsLargeHeadline() {
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setLargeHeadline("Large headline");
    defaultAlert.setSmallHeadline("Small headline");
    NotificationContentGenerator notificationContentGenerator = new NotificationContentGenerator(defaultAlert);
    assertEquals("Large headline", notificationContentGenerator.getShortText());
  }
}
