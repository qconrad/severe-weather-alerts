package com.severeweatheralerts.Networking.FetchServices.Requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StringRequestWithUserAgent extends StringRequest {
  public StringRequestWithUserAgent(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
    super(method, url, listener, errorListener);
  }

  @Override
  public Map<String, String> getHeaders() {
    Map<String, String> headers = new HashMap<>();
    headers.put("User-Agent", getUserAgent());
    return headers;
  }

  private String getUserAgent() {
    return "(Severe Weather Alerts Android Client, https://github.com/qconrad/severe-weather-alerts)";
  }
}
