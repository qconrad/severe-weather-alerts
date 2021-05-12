package com.severeweatheralerts.Networking.FetchServices;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public abstract class RequestService {
  protected final Context context;
  protected final String url;
  protected String userAgent;

  public RequestService(Context context, String url) {
    this.context = context;
    this.url = url;
  }

  public void request(RequestCallback callback) {
    addRequestToQueue(getRequestQueue(), getRequest(callback).setRetryPolicy(new DefaultRetryPolicy(5000, 2, 3f)));
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  private boolean noUserAgent() {
    return userAgent == null;
  }

  protected Request getRequest(RequestCallback callback) {
    if (noUserAgent()) return getRequestWithoutUserAgent(callback);
    return getRequestWithUserAgent(callback);
  }

  protected abstract Request getRequestWithUserAgent(RequestCallback callback);
  protected abstract Request getRequestWithoutUserAgent(RequestCallback callback);

  private void addRequestToQueue(RequestQueue queue, Request request) {
    queue.add(request);
  }

  private RequestQueue getRequestQueue() {
    return Volley.newRequestQueue(context);
  }
}
