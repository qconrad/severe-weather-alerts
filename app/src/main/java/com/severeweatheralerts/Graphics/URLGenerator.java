package com.severeweatheralerts.Graphics;

import android.content.Context;

import com.android.volley.VolleyError;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Networking.FetchServices.FetchCallback;
import com.severeweatheralerts.Networking.FetchServices.StringFetchService;
import com.severeweatheralerts.TextUtils.DateTimeConverter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

public class URLGenerator {
  private final GraphicType graphicType;
  private final Bounds bounds;
  private final Context context;
  private URLGenCompleteListener completeListener;
  ArrayList<String> urls = new ArrayList<>();

  public URLGenerator(Context context, GraphicType graphicType, Bounds bounds) {
    this.graphicType = graphicType;
    this.bounds = bounds;
    this.context = context;
  }

  public void generate(URLGenCompleteListener completeListener) {
    this.completeListener = completeListener;
    if (graphicType.getClass().equals(AlertArea.class)) {
      urls.add(new URL().getCountyMap(bounds));
      completeListener.onComplete(urls);
    }
    else getMapTimes();
  }

  public void getMapTimes() {
    ArrayList<Date> mapTimes = new ArrayList<>();
    StringFetchService fetchService = new StringFetchService(context, new URL().getMapTimes("snowamt", "conus"));
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        JSONArray times = null;
        try {
          times = new JSONArray(response.toString());
          for (int i = 0; i < times.length(); i++) {
            mapTimes.add(DateTimeConverter.convertStringToDate(times.getString(i)));
          }
          urls.add(new URL().getTotalSnow(bounds, "conus", times.getJSONArray(times.length()-1).getString(0)));
          urls.add(new URL().getTotalSnowPoints(bounds, "conus", times.getJSONArray(times.length()-1).getString(0)));
        } catch (JSONException e) {
          e.printStackTrace();
        }
        completeListener.onComplete(urls);
      }

      @Override
      public void error(VolleyError error) {
        completeListener.onComplete(null);
      }
    });
  }
}
