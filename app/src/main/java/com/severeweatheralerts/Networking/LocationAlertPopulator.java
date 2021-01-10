package com.severeweatheralerts.Networking;

import com.severeweatheralerts.Adapters.AlertAdapter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.JSONParsing.AlertParser;
import com.severeweatheralerts.Location;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LocationAlertPopulator {
  public static void populateLocationWithAlertsForThatLocation(Location location) throws IOException {
    String alertData = getAlertData();
    ArrayList<Alert> alerts = convertDataToAlerts(alertData);
    location.setAlerts(alerts);
  }

  private static ArrayList<Alert> convertDataToAlerts(String alertData) {
    return new AlertAdapter(new AlertParser(alertData).getParsedAlerts()).getAdaptedAlerts();
  }

  private static String getAlertData() throws IOException {
    return inputStreamToString(fetchData());
  }

  private static InputStream fetchData() throws IOException {
    return (InputStream) new DataFetchService("https://api.weather.gov/alerts?status=actual").fetchData();
  }

  private static String inputStreamToString(InputStream inputStream) throws IOException {
    ByteArrayOutputStream into = new ByteArrayOutputStream();
    byte[] buf = new byte[4096];
    for (int n; 0 < (n = inputStream.read(buf));) { into.write(buf, 0, n); }
    into.close();
    return new String(into.toByteArray(), "UTF-8");
  }
}
