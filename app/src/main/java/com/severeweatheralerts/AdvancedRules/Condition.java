package com.severeweatheralerts.AdvancedRules;

public interface Condition {
  String getDescription();
  boolean isMet(Criteria criteria);
  void setInverted(boolean isInverted);
  boolean isInverted();
}
