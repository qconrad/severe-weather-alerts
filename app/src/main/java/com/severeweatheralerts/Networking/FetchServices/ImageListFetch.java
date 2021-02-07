package com.severeweatheralerts.Networking.FetchServices;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

public class ImageListFetch extends StringListFetch {
  protected ArrayList<Bitmap> bitmaps = new ArrayList<>();

  public ImageListFetch(Context context, ArrayList<String> urls) {
    super(context, urls);
  }

  @Override
  protected void addData(Object response) {
    bitmaps.add((Bitmap)response);
  }

  protected Object getReturnData() {
    return bitmaps;
  }

  protected FetchService getFetchService(String url) {
    return new ImageFetchService(context, url);
  }
}
