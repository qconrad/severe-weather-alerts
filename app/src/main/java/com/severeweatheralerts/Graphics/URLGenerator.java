package com.severeweatheralerts.Graphics;

import java.util.ArrayList;

public class URLGenerator {
  private final GraphicType graphicType;
  private final Bounds bounds;

  public URLGenerator(GraphicType graphicType, Bounds bounds) {
    this.graphicType = graphicType;
    this.bounds = bounds;
  }

  public void generate(URLGenCompleteListener completeListener) {
    ArrayList<String> urls = new ArrayList<>();
      //urls.add(new GraphicURLs().getTotalSnow(bounds, "conus", "2021-02-08T10:00"));
     urls.add(new GraphicURLs().getCountyMap(bounds));
    //}
    completeListener.onComplete(urls);
  }
}
