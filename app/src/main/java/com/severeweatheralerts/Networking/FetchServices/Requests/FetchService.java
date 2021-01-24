package com.severeweatheralerts.Networking.FetchServices.Requests;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.severeweatheralerts.Networking.FetchServices.FetchCallback;

public abstract class FetchService {
  protected final Context context;
  protected final String url;

  public FetchService(Context context, String url) {
    this.context = context;
    this.url = url;
  }

  public void fetch(FetchCallback callback) {
    addRequestToQueue(getRequestQueue(), getRequest(callback));
  }

  protected abstract Request getRequest(FetchCallback callback);

  private void addRequestToQueue(RequestQueue queue, Request request) {
    queue.add(request);
  }

  private RequestQueue getRequestQueue() {
    return Volley.newRequestQueue(context);
  }
}
