package com.severeweatheralerts.Networking.FetchServices.Requests;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;

import java.util.HashMap;
import java.util.Map;

public class ImageRequestWithUserAgent extends ImageRequest {
  private final String userAgent;
  public ImageRequestWithUserAgent(String url, Response.Listener<Bitmap> listener, int maxWidth, int maxHeight, ImageView.ScaleType scaleType, Bitmap.Config decodeConfig, @Nullable Response.ErrorListener errorListener, String userAgent) {
    super(url, listener, maxWidth, maxHeight, scaleType, decodeConfig, errorListener);
    this.userAgent = userAgent;
  }

  @Override
  public Map<String, String> getHeaders() {
    Map<String, String> headers = new HashMap<>();
    headers.put("User-Agent", userAgent);
    return headers;
  }
}
