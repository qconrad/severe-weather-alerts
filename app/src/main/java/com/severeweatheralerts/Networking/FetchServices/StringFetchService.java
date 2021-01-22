package com.severeweatheralerts.Networking.FetchServices;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.severeweatheralerts.Networking.FetchServices.Requests.StringRequestWithUserAgent;

public class StringFetchService {
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

  private void addRequestToQueue(RequestQueue queue, StringRequest stringRequest) {
    queue.add(stringRequest);
  }

  private StringRequest getRequest(FetchCallback callback) {
    return new StringRequestWithUserAgent(
            StringRequest.Method.GET,
            url,
            callback::success,
            error -> callback.error(error.toString()));
  }

  private RequestQueue getRequestQueue() {
    return Volley.newRequestQueue(context);
  }
}
