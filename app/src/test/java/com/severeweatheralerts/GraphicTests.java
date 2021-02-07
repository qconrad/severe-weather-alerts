package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.Graphic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GraphicTests {
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
