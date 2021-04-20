package com.severeweatheralerts.Status;

import java.util.ArrayList;

public interface Status {
  int getColor();
  int getIcon();
  String getHeadline();
  ArrayList<String> getSubtexts();
}
