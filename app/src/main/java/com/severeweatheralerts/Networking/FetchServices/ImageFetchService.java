package com.severeweatheralerts.Networking.FetchServices;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.ImageRequest;
import com.severeweatheralerts.Networking.FetchServices.Requests.ImageRequestWithUserAgent;

public class ImageFetchService extends RequestService {
  public ImageFetchService(Context context, String url) {
    super(context, url);
  }

  @Override
  protected Request getRequestWithUserAgent(RequestCallback callback) {
   return new ImageRequestWithUserAgent(url, callback::success, 512, 512, null, null, callback::error, userAgent);
  }

  @Override
  protected Request getRequestWithoutUserAgent(RequestCallback callback) {
    return new ImageRequest(url, callback::success, 512, 512, null, null, callback::error);
  }
}
