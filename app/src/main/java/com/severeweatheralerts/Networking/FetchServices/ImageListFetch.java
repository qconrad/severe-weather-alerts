package com.severeweatheralerts.Networking.FetchServices;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Arrays;

public class ImageListFetch extends StringListFetch {
  protected final Bitmap[] bitmaps;

  public ImageListFetch(Context context, ArrayList<String> urls) {
    super(context, urls);
    bitmaps = new Bitmap[urls.size()];
  }

  @Override
  protected void addData(Object response, int index) {
    bitmaps[index] = (Bitmap) response;
  }

  protected Object getReturnData() {
    return new ArrayList<>(Arrays.asList(bitmaps));
  }

  protected FetchService getFetchService(String url) {
    return new ImageFetchService(context, url);
  }
}
