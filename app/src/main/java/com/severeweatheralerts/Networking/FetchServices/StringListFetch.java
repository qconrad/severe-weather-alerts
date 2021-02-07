package com.severeweatheralerts.Networking.FetchServices;

import android.content.Context;

import com.android.volley.VolleyError;

import java.util.ArrayList;

public class StringListFetch {
  protected final Context context;
  private final ArrayList<String> urls;
  private int fetchedCount = 0;
  protected final ArrayList<String> stringData = new ArrayList<>();
  private String userAgent = null;

  public StringListFetch(Context context, ArrayList<String> urls) {
    this.context = context;
    this.urls = urls;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  public void fetch(FetchCallback callback) {
    for (int i = 0; i < urls.size(); i++) {
      String curUrl = urls.get(i);
      FetchService fetchService = getFetchService(curUrl);
      fetchService.setUserAgent(userAgent);
      fetchService.fetch(new FetchCallback() {
        @Override
        public void success(Object response) {
          addData(response);
          fetchedCount++;
          if (!hasMoreUrls())
            callback.success(getReturnData());
        }

        @Override
        public void error(VolleyError error) {
          callback.error(error); }
      });
    }
  }

  protected Object getReturnData() {
    return stringData;
  }

  protected FetchService getFetchService(String url) {
    return new StringFetchService(context, url);
  }

  private boolean hasMoreUrls() {
    return fetchedCount < urls.size();
  }

  protected void addData(Object response) {
    stringData.add(response.toString());
  }
}
