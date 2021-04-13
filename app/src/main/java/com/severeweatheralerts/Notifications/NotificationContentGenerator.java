package com.severeweatheralerts.Notifications;

import com.severeweatheralerts.Alerts.Alert;

public class NotificationContentGenerator {
  private final String name;
  private final Alert.Type type;
  private final String largeHeadline;
  private final String smallHeadline;
  private final String description;
  private final String instruction;

  public NotificationContentGenerator(Alert alert) {
    name = alert.getName();
    type = alert.getType();
    description = alert.getDescription();
    largeHeadline = alert.getLargeHeadline();
    smallHeadline = alert.getSmallHeadline();
    instruction = alert.getInstruction();
  }

  public String getLongText() {
    String content = "";
    if (has(largeHeadline)) content += largeHeadline + "\n\n";
    if (has(smallHeadline)) content += smallHeadline + "\n\n";
    content += description;
    if (has(instruction)) content += "\n\n" + instruction;
    return content;
  }

  public String getShortText() {
    if (has(largeHeadline)) return largeHeadline;
    if (has(smallHeadline)) return smallHeadline;
    return null;
  }

  private boolean has(String largeHeadline) {
    return largeHeadline != null;
  }

  public String getTitleText() {
    if (type == Alert.Type.UPDATE) return name + " Update";
    if (type == Alert.Type.CANCEL) return name + " Cancellation";
    return name;
  }
}
