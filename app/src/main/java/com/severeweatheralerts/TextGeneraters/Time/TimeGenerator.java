package com.severeweatheralerts.TextGeneraters.Time;

public interface TimeGenerator {
  boolean hasLeftTime();
  boolean hasCenterTime();
  boolean hasRightTime();
  String getLeftTime();
  String getCenterTime();
  String getRightTime();
}
