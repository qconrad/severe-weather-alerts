package com.severeweatheralerts.Networking.LocationPopulaters;

import com.severeweatheralerts.Adapters.AlertAdapter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.JSONParsing.AlertParser;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Networking.DataFetchService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AllNWSPopulater {
  protected Location location;
  public AllNWSPopulater(Location location) {
    this.location = location;
  }

  public void populate() throws IOException {
    setAlertsForLocation(convertDataToAlerts(getAlertData()));
  }

  private void setAlertsForLocation(ArrayList<Alert> alerts) {
    location.setAlerts(alerts);
  }

  private ArrayList<Alert> convertDataToAlerts(String alertData) {
    return new AlertAdapter(new AlertParser(alertData).getParsedAlerts()).getAdaptedAlerts();
  }

  private String getAlertData() throws IOException {
    return inputStreamToString(fetchData());
  }

  private InputStream fetchData() throws IOException {
    return (InputStream) new DataFetchService(getUrl()).fetchData();
  }

  protected String getUrl() {
    return "https://api.weather.gov/alerts?status=actual";
  }

  private String inputStreamToString(InputStream inputStream) throws IOException {
    ByteArrayOutputStream into = new ByteArrayOutputStream();
    byte[] buf = new byte[4096];
    for (int n; 0 < (n = inputStream.read(buf));) { into.write(buf, 0, n); }
    into.close();
    return new String(into.toByteArray(), "UTF-8");
  }
}
