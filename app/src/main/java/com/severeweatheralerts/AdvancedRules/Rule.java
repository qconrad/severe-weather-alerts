package com.severeweatheralerts.AdvancedRules;

import java.util.ArrayList;

public interface Rule {
  String getName();
  ConditionGroup getRootConditionGroup();
  ArrayList<Action> getActions();
}
