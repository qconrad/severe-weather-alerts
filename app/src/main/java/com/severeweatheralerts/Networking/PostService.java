package com.severeweatheralerts.Networking;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.severeweatheralerts.Networking.FetchServices.RequestCallback;
import com.severeweatheralerts.Networking.FetchServices.RequestService;

public class PostService extends RequestService {
  private final String body;

  public PostService(Context context, String url, String body) {
    super(context, url);
    this.body = body;
  }

  @Override
  protected Request getRequestWithUserAgent(RequestCallback callback) {
    return null;
  }

  @Override
  protected Request getRequestWithoutUserAgent(RequestCallback callback) {
    return new StringRequest(
            StringRequest.Method.POST,
            url,
            callback::success,
            callback::error) {
      @Override
      public String getBodyContentType() {
        return "application/json; charset=utf-8";
      }
      @Override
      public byte[] getBody() {
        return body.getBytes();
      }
    };
  }
}
