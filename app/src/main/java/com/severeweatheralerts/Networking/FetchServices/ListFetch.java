package com.severeweatheralerts.Networking.FetchServices;

import android.content.Context;

import com.android.volley.VolleyError;

import java.util.ArrayList;

public class ListFetch {
  private final Context context;
  private final ArrayList<String> urls;
  private int fetchCount = 0;
  private final ArrayList<String> fetchedData = new ArrayList<>();
  private String userAgent = null;

  public ListFetch(Context context, ArrayList<String> urls) {
    this.context = context;
    this.urls = urls;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  public void fetch(FetchCallback callback) {
    for (int i = 0; i < urls.size(); i++) {
      String curUrl = urls.get(i);
      StringFetchService stringFetchService = new StringFetchService(context, curUrl);
      stringFetchService.setUserAgent(userAgent);
      stringFetchService.fetch(new FetchCallback() {
        @Override
        public void success(Object response) {
          if (hasMoreUrls()) addData(response.toString());
          else callback.success(fetchedData);
        }

        @Override
        public void error(VolleyError error) { callback.error(error); }
      });
    }
  }

  private boolean hasMoreUrls() {
    return fetchCount < urls.size() - 2;
  }

  private void addData(String response) {
    fetchedData.add(response);
    fetchCount++;
  }
}
