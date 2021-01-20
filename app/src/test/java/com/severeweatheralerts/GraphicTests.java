package com.severeweatheralerts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GraphicTests {
  @Test
  public void getTitle_TitleIsSet_ReturnsThatTitle() {
    Graphic graphic = new Graphic();
    graphic.setTitle("test");
    assertEquals("test", graphic.getTitle());
  }

  @Test
  public void getTitle_DifferentTitleIsSet_ReturnsThatTitle() {
    Graphic graphic = new Graphic();
    graphic.setTitle("title");
    assertEquals("title", graphic.getTitle());
  }

  @Test
  public void getSubtext_SubTextGiven_ThatSubTextReturn() {
    Graphic graphic = new Graphic();
    graphic.setSubtext("subText");
    assertEquals("subText", graphic.getSubtext());
  }

  @Test
  public void getSubtext_DifferentSubTextGiven_ThatSubTextReturn() {
    Graphic graphic = new Graphic();
    graphic.setSubtext("text");
    assertEquals("text", graphic.getSubtext());
  }
}
