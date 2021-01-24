package com.severeweatheralerts.Networking.FetchServices;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.ImageRequest;
import com.severeweatheralerts.Networking.FetchServices.Requests.FetchService;

public class ImageFetchService extends FetchService {
  public ImageFetchService(Context context, String url) {
    super(context, url);
  }

  protected Request getRequest(FetchCallback callback) {
    return new ImageRequest(url, callback::success, 512, 512, null, null, callback::error);
  }
}
