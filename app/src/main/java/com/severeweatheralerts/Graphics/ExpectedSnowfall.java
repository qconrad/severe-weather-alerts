package com.severeweatheralerts.Graphics;

import java.util.ArrayList;

public class ExpectedSnowfall extends AlertArea implements GraphicType  {
  public ExpectedSnowfall() {
  }

  @Override
  public String getTitle() {
    return "Expected Snowfall";
  }

  @Override
  public ArrayList<String> getURLs(Bounds bounds) {
    ArrayList<String> urls = new ArrayList<>();
    urls.add(new URLGenerator().getTotalSnow(bounds, "conus", "2021-02-08T10:00"));
    urls.add(new URLGenerator().getCountyMap(bounds));
    return urls;
  }
}
