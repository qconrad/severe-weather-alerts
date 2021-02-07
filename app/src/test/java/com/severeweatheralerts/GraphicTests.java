package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.Graphic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

  @Test
  public void hasSubtext_NoneGiven_False() {
    Graphic graphic = new Graphic();
    assertFalse(graphic.hasSubtext());
  }

  @Test
  public void hasSubtext_SubtextGiven_True() {
    Graphic graphic = new Graphic();
    graphic.setSubtext("test");
    assertTrue(graphic.hasSubtext());
  }
}
