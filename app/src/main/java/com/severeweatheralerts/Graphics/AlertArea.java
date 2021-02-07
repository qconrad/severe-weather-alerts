package com.severeweatheralerts.Graphics;

import java.util.ArrayList;

public class AlertArea implements GraphicType {
  public AlertArea() { }

  public String getTitle() {
    return "Alert Area";
  }

  public boolean renderZoneMap() {
    return true;
  }

  public ArrayList<String> getURLs(Bounds bounds) {
    ArrayList<String> urls = new ArrayList<>();
    urls.add(new URLGenerator().getCountyMap(bounds));
    return urls;
  }
}
