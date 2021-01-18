package com.severeweatheralerts.Networking;

import android.os.AsyncTask;

import java.io.IOException;

public class AsyncPost extends AsyncTask<Void, Void, Void> {
  private final String url;
  private final String data;

  public AsyncPost(String url, String data) {
    this.url = url;
    this.data = data;
  }

  @Override
  protected Void doInBackground(Void... ignored) {
    try { new DataPostService(url, data).post(); }
    catch (IOException e) { e.printStackTrace(); }
    return null;
  }
}
