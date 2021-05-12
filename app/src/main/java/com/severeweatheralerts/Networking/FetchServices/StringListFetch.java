package com.severeweatheralerts.Networking.FetchServices;

import android.content.Context;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Arrays;

public class StringListFetch {
  protected final Context context;
  private final ArrayList<String> urls;
  private int fetchedCount = 0;
  private String userAgent = null;
  private final String[] stringArr;

  public StringListFetch(Context context, ArrayList<String> urls) {
    this.context = context;
    this.urls = urls;
    stringArr = new String[urls.size()];
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  private int i;
  public void fetch(RequestCallback callback) {
    for (i = 0; i < urls.size(); i++) {
      String curUrl = urls.get(i);
      if (curUrl == null) { fetchedCount++; continue; }
      RequestService fetchService = getFetchService(curUrl);
      fetchService.setUserAgent(userAgent);
      fetchService.request(new RequestCallback() {
        private final int index = i;
        @Override
        public void success(Object response) {
          addData(response, index);
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
    return new ArrayList<>(Arrays.asList(stringArr));
  }

  protected RequestService getFetchService(String url) {
    return new StringFetchService(context, url);
  }

  private boolean hasMoreUrls() {
    return fetchedCount < urls.size();
  }

  protected void addData(Object response, int index) {
    stringArr[index] = response.toString();
  }
}
