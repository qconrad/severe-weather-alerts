package com.severeweatheralerts.Networking.FetchServices;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.severeweatheralerts.Networking.FetchServices.Requests.StringRequestWithUserAgent;

public class StringFetchService {
  private String userAgent;

  private final Context context;
  private final String url;

  public StringFetchService(Context context, String url) {
    this.context = context;
    this.url = url;
  }

  public void fetch(FetchCallback callback) {
    RequestQueue queue = getRequestQueue();
    StringRequest stringRequest = getRequest(callback);
    addRequestToQueue(queue, stringRequest);
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  private void addRequestToQueue(RequestQueue queue, StringRequest stringRequest) {
    queue.add(stringRequest);
  }

  private StringRequest getRequest(FetchCallback callback) {
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
            error -> callback.error(error.toString()));
  }

  private StringRequest getRequestWithUserAgent(FetchCallback callback) {
    return new StringRequestWithUserAgent(
            StringRequest.Method.GET,
            url,
            callback::success,
            error -> callback.error(error.toString()), userAgent);
  }

  private RequestQueue getRequestQueue() {
    return Volley.newRequestQueue(context);
  }
}
