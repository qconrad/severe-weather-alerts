package com.severeweatheralerts.Graphics.URLGeneration;

import android.content.Context;

import com.android.volley.VolleyError;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Graphics.Bounds.Bound;
import com.severeweatheralerts.Graphics.GridData.Parameter;
import com.severeweatheralerts.JSONParsing.MapTimeParser;
import com.severeweatheralerts.Networking.FetchServices.FetchCallback;
import com.severeweatheralerts.Networking.FetchServices.StringFetchService;

import java.util.ArrayList;

public abstract class MapTimeFetch implements URLGenerator {
  protected final Bound bound;
  private final Context context;
  protected final Parameter gridData;
  private URLGenCompleteListener completeListener;
  ArrayList<String> urls = new ArrayList<>();
  public MapTimeFetch(Context context, Bound bound, Parameter gridData) {
    this.bound = bound;
    this.context = context;
    this.gridData = gridData;
  }

  public void generate(URLGenCompleteListener completeListener) {
    this.completeListener = completeListener;
    getMapTimes();
  }

  public void getMapTimes() {
    StringFetchService fetchService = new StringFetchService(context, new URL().getMapTimes("snowamt", "conus"));
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        getURLS(new MapTimeParser(response.toString()).getDateStrings());
        completeListener.onComplete(urls);
      }

      @Override
      public void error(VolleyError error) {
        completeListener.onComplete(null);
      }
    });
  }


  public abstract void getURLS(ArrayList<String> dateStrings);
}
