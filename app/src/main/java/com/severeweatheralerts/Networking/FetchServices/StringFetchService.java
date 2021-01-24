package com.severeweatheralerts.Networking.FetchServices;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.severeweatheralerts.Networking.FetchServices.Requests.FetchService;
import com.severeweatheralerts.Networking.FetchServices.Requests.StringRequestWithUserAgent;

public class StringFetchService extends FetchService {
  private String userAgent;

  public StringFetchService(Context context, String url) {
    super(context, url);
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  protected Request getRequest(FetchCallback callback) {
    if (noUserAgent()) return getRequestWithoutUserAgent(callback);
    return getRequestWithUserAgent(callback);
  }

  private boolean noUserAgent() {
    return userAgent == null;
  }

  private StringRequest getRequestWithoutUserAgent(FetchCallback callback) {
    return new StringRequest(
            StringRequest.Method.GET,
            url,
            callback::success,
            callback::error);
  }

  private StringRequest getRequestWithUserAgent(FetchCallback callback) {
    return new StringRequestWithUserAgent(
            StringRequest.Method.GET,
            url,
            callback::success,
            callback::error, userAgent);
  }
}
