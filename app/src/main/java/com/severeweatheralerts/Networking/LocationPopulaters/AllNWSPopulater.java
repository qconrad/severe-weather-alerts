package com.severeweatheralerts.Networking.LocationPopulaters;

import android.content.Context;

import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.severeweatheralerts.Adapters.AlertAdapter;
import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.JSONParsing.AlertListParser;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Networking.FetchServices.FetchCallback;
import com.severeweatheralerts.Networking.FetchServices.StringFetchService;
import com.severeweatheralerts.R;

import java.util.ArrayList;

public class AllNWSPopulater {
  private final Context context;
  protected GCSCoordinate location;

  public AllNWSPopulater(GCSCoordinate location, Context context) {
    this.location = location;
    this.context = context;
  }

  public void populate(PopulateCallback populateCallback) {
    fetchAndSetAlerts(populateCallback);
  }

  private void fetchAndSetAlerts(PopulateCallback populateCallback) {
    StringFetchService stringFetchService = new StringFetchService(context, getUrl());
    stringFetchService.setUserAgent(getUserAgent());
    stringFetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) { setAlertsAndCallback((String) response, populateCallback); }
      @Override
      public void error(VolleyError error) { handleError(error, populateCallback); }
    });
  }

  private void setAlertsAndCallback(String response, PopulateCallback populateCallback) {
    populateCallback.complete(convertDataToAlerts(response));
  }

  private void handleError(VolleyError error, PopulateCallback populateCallback) {
    if (error instanceof ServerError)
      populateCallback.error(context.getString(R.string.server_error));
    else
      populateCallback.error(context.getString(R.string.unknown_alert_error) + error.getMessage());
  }

  private String getUserAgent() {
    return Constants.USER_AGENT;
  }

  private ArrayList<Alert> convertDataToAlerts(String alertData) {
    return new AlertAdapter(new AlertListParser(alertData).getParsedAlerts()).getAdaptedAlerts();
  }

  protected String getUrl() {
    return "https://api.weather.gov/alerts?status=actual";
  }
}
