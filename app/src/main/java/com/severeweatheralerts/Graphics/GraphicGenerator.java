package com.severeweatheralerts.Graphics;

public class GraphicGenerator {
  public GraphicGenerator(GraphicType graphicType) {
  }

  public void generate(GraphicCompleteListener graphicCompleteListener) {
    Graphic graphic = new Graphic();
    graphicCompleteListener.onComplete(graphic);
  }
}
