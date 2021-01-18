package com.severeweatheralerts.Networking;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataPostService {
  private final String postURL;
  private final String data;

  public DataPostService(String postURL, String data) {
    this.postURL = postURL;
    this.data = data;
  }

  public void post() throws IOException {
    HttpURLConnection urlConnection = createHttpURLConnection();
    OutputStream out = createOutputStream(urlConnection);
    urlConnection.connect();

    BufferedWriter writer = createBufferedWriter(out);
    writeToBuffer(writer);
    cleanUp(out, writer);

    urlConnection.getInputStream();
  }

  private void writeToBuffer(BufferedWriter writer) throws IOException {
    writer.write(data);
  }

  private void cleanUp(OutputStream out, BufferedWriter writer) throws IOException {
    writer.flush();
    writer.close();
    out.close();
  }

  private BufferedWriter createBufferedWriter(OutputStream out) throws UnsupportedEncodingException {
    return new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
  }

  private OutputStream createOutputStream(HttpURLConnection urlConnection) throws IOException {
    return new BufferedOutputStream(urlConnection.getOutputStream());
  }

  private HttpURLConnection createHttpURLConnection() throws IOException {
    URL url = new URL(postURL);
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    urlConnection.setRequestProperty("Content-Type", "application/json");
    urlConnection.setRequestMethod("POST");
    return urlConnection;
  }
}
