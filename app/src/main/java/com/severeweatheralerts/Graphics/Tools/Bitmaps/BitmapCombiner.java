package com.severeweatheralerts.Graphics.Tools.Bitmaps;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.ArrayList;

public class BitmapCombiner {
  private final ArrayList<Bitmap> bitmaps;

  public BitmapCombiner(ArrayList<Bitmap> bitmaps) {
    this.bitmaps = bitmaps;
  }

  public Bitmap combine() {
    Bitmap combined = Bitmap.createBitmap(bitmaps.get(0).getWidth(), bitmaps.get(0).getHeight(), bitmaps.get(0).getConfig());
    Canvas canvas = new Canvas(combined);
    for (int i = 0; i < bitmaps.size(); i++)
      canvas.drawBitmap(bitmaps.get(i), new Matrix(), null);
    return combined;
  }
}
