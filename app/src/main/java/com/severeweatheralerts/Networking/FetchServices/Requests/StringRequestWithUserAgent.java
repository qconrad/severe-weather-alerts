package com.severeweatheralerts.Networking.FetchServices.Requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StringRequestWithUserAgent extends StringRequest {
  private final String userAgent;

 public StringRequestWithUserAgent(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener, String userAgent) {
    super(method, url, listener, errorListener);
    this.userAgent = userAgent;
  }

  @Override
  public Map<String, String> getHeaders() {
    Map<String, String> headers = new HashMap<>();
    headers.put("User-Agent", userAgent);
    return headers;
  }
}
