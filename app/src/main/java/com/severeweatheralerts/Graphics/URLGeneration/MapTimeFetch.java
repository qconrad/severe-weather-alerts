package com.severeweatheralerts.Graphics.URLGeneration;

import android.content.Context;

import com.android.volley.VolleyError;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Graphics.Bounds.Bound;
import com.severeweatheralerts.Graphics.PointInfoParser;
import com.severeweatheralerts.JSONParsing.GridDataParser;
import com.severeweatheralerts.JSONParsing.MapTimeParser;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Networking.FetchServices.FetchCallback;
import com.severeweatheralerts.Networking.FetchServices.StringFetchService;

import org.json.JSONException;

import java.util.ArrayList;

public abstract class MapTimeFetch implements URLGenerator {
  protected final Bound bound;
  private final Context context;
  private final Location location;
  private URLGenCompleteListener completeListener;
  ArrayList<String> urls = new ArrayList<>();
  int fetchCount = 0;
  public MapTimeFetch(Context context, Bound bound, Location location) {
    this.bound = bound;
    this.context = context;
    this.location = location;
  }

  private void finish() {
    if (fetchCount++ >= 1) completeListener.onComplete(urls);
  }

  public void generate(URLGenCompleteListener completeListener) {
    this.completeListener = completeListener;
    getMapTimes();
    getPointInfo();
  }

  public void getMapTimes() {
    StringFetchService fetchService = new StringFetchService(context, new URL().getMapTimes("snowamt", "conus"));
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        getURLS(new MapTimeParser(response.toString()).getDateStrings());
        finish();
      }

      @Override
      public void error(VolleyError error) {
        completeListener.onComplete(null);
      }
    });
  }

  public void getPointInfo() {
    StringFetchService fetchService = new StringFetchService(context, new URL().getPointInfo(location.getLatitude(), location.getLongitude()));
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        String url = new PointInfoParser(response.toString()).getForecastGridLink();
        getForecastGridData(url);
      }

      @Override
      public void error(VolleyError error) {
        completeListener.onComplete(null);
      }
    });
  }

  public void getForecastGridData(String gridDataURL) {
    StringFetchService fetchService = new StringFetchService(context, gridDataURL);
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        parseGridData(response);
        finish();
      }

      @Override
      public void error(VolleyError error) {
        completeListener.onComplete(null);
      }
    });
  }

  private void parseGridData(Object response) {
    try { new GridDataParser(response.toString()).getParameter("snowfallAmount"); }
    catch (JSONException e) { completeListener.onComplete(null); }
  }

  public abstract void getURLS(ArrayList<String> dateStrings);
}
