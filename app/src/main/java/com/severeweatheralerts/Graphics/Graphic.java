package com.severeweatheralerts.Graphics;

import android.graphics.Bitmap;

public class Graphic {
  private String subText;
  private Bitmap image;

  public void setSubtext(String subText) { this.subText = subText; }
  public String getSubtext() { return subText; }
  public void setImage(Bitmap image) { this.image = image; }
  public Bitmap getImage() { return image; }
  public boolean hasSubtext() { return subText != null; }
}
