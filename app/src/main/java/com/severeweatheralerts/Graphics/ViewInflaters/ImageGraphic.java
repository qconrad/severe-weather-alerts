package com.severeweatheralerts.Graphics.ViewInflaters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.severeweatheralerts.Graphics.ViewInflaters.Graphic;

public class ImageGraphic implements Graphic {
  protected final Context context;
  protected final Bitmap image;
  private final String subtext;

  public ImageGraphic(Context context, Bitmap image, String subtext) {
    this.context = context;
    this.image = image;
    this.subtext = subtext;
  }

  @Override
  public View getView() {
    return getImageView(20.0f);
  }

  protected ImageView getImageView(float cornerRadius) {
    ImageView iv = new ImageView(context);
    iv.setAdjustViewBounds(true);
    iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
    iv.setImageBitmap(image);
    RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(Resources.getSystem(), image);
    dr.setCornerRadius(cornerRadius);
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
