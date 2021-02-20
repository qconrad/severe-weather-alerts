package com.severeweatheralerts;

public class Clear implements Status {
  @Override
  public void getColor() { }

  @Override
  public int getIcon() {
    return R.drawable.sun;
  }

  @Override
  public String getHeadline() {
    return "You're in the clear!";
  }

  @Override
  public String getSubtext() {
    return "There are no active alerts for this location. When hazadour weather is expected, a push notification will be sent and alerts will snow up here.";
  }
}
