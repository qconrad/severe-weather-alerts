package com.severeweatheralerts;

public class Clear implements Status {
  @Override
  public int getIcon() {
    return 0;
  }

  @Override
  public String getHeadline() {
    return null;
  }

  @Override
  public String getSubtext() {
    return null;
  }
}
