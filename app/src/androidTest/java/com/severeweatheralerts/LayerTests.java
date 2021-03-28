package com.severeweatheralerts;

import android.graphics.Bitmap;

import com.severeweatheralerts.Graphics.Layer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LayerTests {
  @Test
  public void newLayerWithURL_isURL() {
    Layer layer = new Layer("urlhere");
    assertTrue(layer.isURL());
  }

  @Test
  public void newLayerWithBitmap_isNotURL() {
    Layer layer = new Layer(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888));
    assertFalse(layer.isURL());
  }

  @Test
  public void newLayerWithURL_getURLisCorrect() {
    Layer layer = new Layer("urlhere");
    assertEquals("urlhere", layer.getURL());
  }

  @Test
  public void newLayerWithDifferentURL_getURLisCorrect() {
    Layer layer = new Layer("differenturlhere");
    assertEquals("differenturlhere", layer.getURL());
  }

  @Test
  public void newLayerWithBitmap_ReturnsThatBitmap() {
    Layer layer = new Layer(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888));
    assertEquals(100, layer.getBitmap().getHeight());
  }
}
