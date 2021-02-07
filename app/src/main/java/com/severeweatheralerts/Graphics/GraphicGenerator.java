package com.severeweatheralerts.Graphics;

public class GraphicGenerator {
  public GraphicGenerator(GraphicType graphicType) {
  }

  public void generate(GraphicCompleteListener graphicCompleteListener) {
    graphicCompleteListener.onComplete(null);
  }
}
