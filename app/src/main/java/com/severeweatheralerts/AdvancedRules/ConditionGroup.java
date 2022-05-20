package com.severeweatheralerts.AdvancedRules;

import java.util.ArrayList;

public interface ConditionGroup extends Condition {
  ArrayList<Condition> getConditions();
  BooleanCriteria getBooleanCriteria();
}
