package com.severeweatheralerts.Networking.LocationPopulaters;

import android.content.Context;

import com.severeweatheralerts.Adapters.AlertAdapter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.JSONParsing.AlertListParser;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Networking.FetchServices.FetchCallback;
import com.severeweatheralerts.Networking.FetchServices.StringFetchService;

import java.util.ArrayList;

public class AllNWSPopulater {
  private final Context context;
  protected Location location;

  public AllNWSPopulater(Location location, Context context) {
    this.location = location;
    this.context = context;
  }

  public void populate(PopulateCallback populateCallback) {
    fetchAndSetAlerts(populateCallback);
  }

  private void fetchAndSetAlerts(PopulateCallback populateCallback) {
    new StringFetchService(context, getUrl()).fetch(new FetchCallback() {
      @Override
      public void success(String response) {
        setAlertsForLocation(convertDataToAlerts(response));
        populateCallback.complete();
      }

      @Override
      public void error(String message) {
        populateCallback.error(message);
      }
    });
  }

  private void setAlertsForLocation(ArrayList<Alert> alerts) {
    location.setAlerts(alerts);
  }

  private ArrayList<Alert> convertDataToAlerts(String alertData) {
    return new AlertAdapter(new AlertListParser(alertData).getParsedAlerts()).getAdaptedAlerts();
  }

  protected String getUrl() {
    return "https://api.weather.gov/alerts?status=actual";
  }
}
