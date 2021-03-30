package com.severeweatheralerts.Networking.FetchServices;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.severeweatheralerts.Networking.FetchServices.FetchCallback;

public abstract class FetchService {
  protected final Context context;
  protected final String url;
  protected String userAgent;

  public FetchService(Context context, String url) {
    this.context = context;
    this.url = url;
  }

  public void fetch(FetchCallback callback) {
    addRequestToQueue(getRequestQueue(), getRequest(callback).setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 3, 1.25f)));
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  private boolean noUserAgent() {
    return userAgent == null;
  }

  protected Request getRequest(FetchCallback callback) {
    if (noUserAgent()) return getRequestWithoutUserAgent(callback);
    return getRequestWithUserAgent(callback);
  }

  protected abstract Request getRequestWithUserAgent(FetchCallback callback);
  protected abstract Request getRequestWithoutUserAgent(FetchCallback callback);

  private void addRequestToQueue(RequestQueue queue, Request request) {
    queue.add(request);
  }

  private RequestQueue getRequestQueue() {
    return Volley.newRequestQueue(context);
  }
}
