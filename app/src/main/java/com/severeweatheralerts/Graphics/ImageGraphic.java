package com.severeweatheralerts.Graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

public class ImageGraphic implements Graphic {
  private final Context context;
  private final Bitmap image;
  private String subtext;

  public ImageGraphic(Context context, Bitmap image, String subtext) {
    this.context = context;
    this.image = image;
    this.subtext = subtext;
  }

  @Override
  public View getView() {
    ImageView iv = new ImageView(context);
    iv.setAdjustViewBounds(true);
    iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
    iv.setImageBitmap(image);
    RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(Resources.getSystem(), image);
    dr.setCornerRadius(20.0f);
    iv.setImageDrawable(dr);
    return iv;
  }

  @Override
  public boolean hasSubtext() {
    return subtext != null;
  }

  @Override
  public String getSubtext() {
    return subtext;
  }
}
