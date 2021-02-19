package com.severeweatheralerts.TextUtils;

public class Plurality {
  private final String single;
  private final String plural;
  private final boolean isPlural;

  public Plurality(double value, String single, String plural) {
    isPlural = value != 1;
    this.single = single;
    this.plural = plural;
  }

  public String getText() {
    if (isPlural) return plural;
    return single;
  }
}
