package com.severeweatheralerts.JSONParsing;

import org.json.JSONException;
import org.json.JSONObject;

public class GeocodeParser {
  private boolean hasError;
  private String postal;
  private String address;
  private String city;
  private double longitude;
  private double latitude;
  private String error;

  public GeocodeParser(String geocodeString) {
    try {
      JSONObject geocode = new JSONObject(geocodeString);
      if (geocode.has("error")) {
        hasError = true;
        error = geocode.getJSONObject("error").getString("description");
      } else {
        latitude = geocode.getDouble("latt");
        longitude = geocode.getDouble("longt");
        city = geocode.getJSONObject("standard").getString("city");
        if (!geocode.getJSONObject("standard").getString("postal").equals("{}")) {
          postal = geocode.getJSONObject("standard").getString("postal");
        }
        address = geocode.getJSONObject("standard").getString("stnumber") + " " +
                geocode.getJSONObject("standard").getString("addresst");
      }
    }
    catch (JSONException e) { e.printStackTrace(); }
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public String getCity() {
    return city;
  }

  public String getAddress() {
    return address;
  }

  public String getPostal() {
    return postal;
  }

  public boolean hasError() {
    return hasError;
  }

  public String getError() {
    return error;
  }
}
