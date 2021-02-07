package com.severeweatheralerts.Graphics;

import java.util.ArrayList;

public class GraphicURLGenerator {
  private final GraphicType graphicType;
  private final Bounds bounds;

  public GraphicURLGenerator(GraphicType graphicType, Bounds bounds) {
    this.graphicType = graphicType;
    this.bounds = bounds;
  }

  public void generate(URLGenCompleteListener completeListener) {
    ArrayList<String> urls = new ArrayList<>();
      //urls.add(new URLGenerator().getTotalSnow(bounds, "conus", "2021-02-08T10:00"));
     urls.add(new URLGenerator().getCountyMap(bounds));
    //}
    completeListener.onComplete(urls);
  }
}
