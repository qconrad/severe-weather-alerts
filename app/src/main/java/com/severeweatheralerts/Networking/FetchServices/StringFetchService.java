package com.severeweatheralerts.Networking.FetchServices;

import android.content.Context;

import com.android.volley.toolbox.StringRequest;
import com.severeweatheralerts.Networking.FetchServices.Requests.StringRequestWithUserAgent;

public class StringFetchService extends FetchService {
  public StringFetchService(Context context, String url) {
    super(context, url);
  }

  @Override
  protected StringRequest getRequestWithoutUserAgent(FetchCallback callback) {
    return new StringRequest(
            StringRequest.Method.GET,
            url,
            callback::success,
            callback::error);
  }

  @Override
  protected StringRequest getRequestWithUserAgent(FetchCallback callback) {
    return new StringRequestWithUserAgent(
            StringRequest.Method.GET,
            url,
            callback::success,
            callback::error, userAgent);
  }
}
