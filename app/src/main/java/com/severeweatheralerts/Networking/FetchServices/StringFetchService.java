package com.severeweatheralerts.Networking.FetchServices;

import android.content.Context;

import com.android.volley.toolbox.StringRequest;
import com.severeweatheralerts.Networking.FetchServices.Requests.StringRequestWithUserAgent;

public class StringFetchService extends RequestService {
  public StringFetchService(Context context, String url) {
    super(context, url);
  }

  @Override
  protected StringRequest getRequestWithoutUserAgent(RequestCallback callback) {
    return new StringRequest(
            StringRequest.Method.GET,
            url,
            callback::success,
            callback::error);
  }

  @Override
  protected StringRequest getRequestWithUserAgent(RequestCallback callback) {
    return new StringRequestWithUserAgent(
            StringRequest.Method.GET,
            url,
            callback::success,
            callback::error, userAgent);
  }
}
