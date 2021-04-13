package com.severeweatheralerts.JSONParsing;

import org.json.JSONException;
import org.json.JSONObject;

public class GeocodeParser {
  private String postal;
  private String address;
  private String city;
  private double longitude;
  private double latitude;
  private JSONObject geocode;

  public GeocodeParser(String geocodeString) {
    try {
      geocode = new JSONObject(geocodeString);
      latitude = geocode.getDouble("latt");
      longitude = geocode.getDouble("longt");
      city = geocode.getJSONObject("standard").getString("city");
      postal = geocode.getJSONObject("standard").getString("postal");
      address = geocode.getJSONObject("standard").getString("stnumber") + " " +
              geocode.getJSONObject("standard").getString("addresst");
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
}
