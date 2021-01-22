package com.severeweatheralerts;

import android.graphics.Bitmap;

import com.severeweatheralerts.Graphics.Graphic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GraphicImageTests {
  @Test
  public void getImage_BitmapAdded_SamHeightReturned() {
    Graphic graphic = new Graphic();
    Bitmap bm = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
    graphic.setImage(bm);
    assertEquals(100, graphic.getImage().getHeight());
  }

  @Test
  public void getImage_DifferentBitmapAdded_SameHeightReturn() {
    Graphic graphic = new Graphic();
    Bitmap bm = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
    graphic.setImage(bm);
    assertEquals(50, graphic.getImage().getHeight());
  }
}
