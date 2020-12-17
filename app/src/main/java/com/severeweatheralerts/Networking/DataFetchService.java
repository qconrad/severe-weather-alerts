package com.severeweatheralerts.Networking;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class DataFetchService {
  private final int TIMEOUT = 60000;
  private String fetchUrl;

  public DataFetchService(String url) {
    fetchUrl = url;
  }

  public Object fetchData() throws IOException {
    URL url = new URL(fetchUrl);
    URLConnection request = url.openConnection();
    request.setConnectTimeout(TIMEOUT);
    request.setReadTimeout(TIMEOUT);
    request.setRequestProperty("User-Agent", "(Severe Weather Alerts Android Client, https://github.com/qconrad/severe-weather-alerts)");
    request.connect();
    return request.getContent();
  }
}
