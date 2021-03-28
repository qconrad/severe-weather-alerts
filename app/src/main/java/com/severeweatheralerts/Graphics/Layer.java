package com.severeweatheralerts.Graphics;

import android.graphics.Bitmap;

public class Layer {
  private Bitmap bitmap;
  private String url;

  public Layer(String url) {
    this.url = url;
  }

  public Layer(Bitmap bitmap) {
    this.bitmap = bitmap;
  }

  public boolean isURL() {
    return url != null;
  }

  public String getURL() {
    return url;
  }

  public Bitmap getBitmap() {
    return bitmap;
  }
}
