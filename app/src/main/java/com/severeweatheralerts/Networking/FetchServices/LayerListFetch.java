package com.severeweatheralerts.Networking.FetchServices;

import android.content.Context;
import android.graphics.Bitmap;

import com.severeweatheralerts.Graphics.Layer;

import java.util.ArrayList;
import java.util.Arrays;

public class LayerListFetch extends StringListFetch {
  protected final Bitmap[] bitmaps;

  public LayerListFetch(Context context, ArrayList<Layer> layers) {
    super(context, getURLs(layers));
    bitmaps = new Bitmap[layers.size()];
    setBitmaps(layers);
  }

  private void setBitmaps(ArrayList<Layer> layers) {
    for (int i = 0; i < layers.size(); i++)
      if (!layers.get(i).isURL()) bitmaps[i] = layers.get(i).getBitmap();
  }

  private static ArrayList<String> getURLs(ArrayList<Layer> layers) {
    ArrayList<String> urls = new ArrayList<>();
    for (Layer layer : layers) urls.add(layer.getURL());
    return urls;
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
