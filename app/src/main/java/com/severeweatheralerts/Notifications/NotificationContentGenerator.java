package com.severeweatheralerts.Notifications;

import com.severeweatheralerts.Alerts.Alert;

public class NotificationContentGenerator {
  private final String largeHeadline;
  private final String smallHeadline;
  private final String description;
  private final String instruction;

  public NotificationContentGenerator(Alert alert) {
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

}
