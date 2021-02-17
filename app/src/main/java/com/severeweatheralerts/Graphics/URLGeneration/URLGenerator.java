package com.severeweatheralerts.Graphics.URLGeneration;

import android.content.Context;

import com.android.volley.VolleyError;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Graphics.Bounds.Bound;
import com.severeweatheralerts.Graphics.Types.AlertArea;
import com.severeweatheralerts.Graphics.Types.GraphicType;
import com.severeweatheralerts.JSONParsing.MapTimeParser;
import com.severeweatheralerts.Networking.FetchServices.FetchCallback;
import com.severeweatheralerts.Networking.FetchServices.StringFetchService;

import java.util.ArrayList;

public class URLGenerator {
  private final GraphicType graphicType;
  private final Bound bounds;
  private final Context context;
  private URLGenCompleteListener completeListener;
  ArrayList<String> urls = new ArrayList<>();

  public URLGenerator(Context context, GraphicType graphicType, Bound bounds) {
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
    StringFetchService fetchService = new StringFetchService(context, new URL().getMapTimes("snowamt", "conus"));
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        MapTimeParser mapTimeParser = new MapTimeParser(response.toString());
        String LatestTime = mapTimeParser.getDateStrings().get(mapTimeParser.getDateStrings().size()-1);
        urls.add(new URL().getTotalSnow(bounds, "conus", LatestTime));
        urls.add(new URL().getTotalSnowPoints(bounds, "conus", LatestTime));
        urls.add(new URL().getCountyMap(bounds));
        completeListener.onComplete(urls);
      }

      @Override
      public void error(VolleyError error) {
        completeListener.onComplete(null);
      }
    });
  }
}
