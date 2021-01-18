package com.severeweatheralerts;

import android.os.AsyncTask;

import com.severeweatheralerts.Networking.DataPostService;

import java.io.IOException;

public class AsyncPost extends AsyncTask<String, Void, Void> {
  @Override
  protected Void doInBackground(String... params) {
    try { new DataPostService(params[0], params[1]).post(); }
    catch (IOException e) { e.printStackTrace(); }
    return null;
  }
}
