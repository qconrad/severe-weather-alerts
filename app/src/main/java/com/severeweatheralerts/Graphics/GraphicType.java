package com.severeweatheralerts.Graphics;

import java.util.ArrayList;

public interface GraphicType {
  String getTitle();
  boolean renderZoneMap();
  ArrayList<String> getURLs(Bounds bounds);
}
