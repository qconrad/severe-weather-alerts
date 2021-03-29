package com.severeweatheralerts.Notifications;

import com.severeweatheralerts.Alerts.Alert;

public class NotificationContentGenerator {
  private final Alert alert;

  public NotificationContentGenerator(Alert alert) {
    this.alert = alert;
  }

  public String getContent() {
    String content = "";
    String largeHeadline = alert.getLargeHeadline();
    String smallHeadline = alert.getSmallHeadline();
    String instruction = alert.getInstruction();
    if (has(largeHeadline)) content += largeHeadline + "\n\n";
    if (has(smallHeadline)) content += alert.getSmallHeadline() + "\n\n";
    content += alert.getDescription();
    if (has(instruction)) content += "\n\n" + instruction;
    return content;
  }

  private boolean has(String largeHeadline) {
    return largeHeadline != null;
  }
}
