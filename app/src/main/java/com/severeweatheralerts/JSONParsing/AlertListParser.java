package com.severeweatheralerts.JSONParsing;

import com.severeweatheralerts.Adapters.UnadaptedAlert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AlertListParser {
  ArrayList<UnadaptedAlert> parsed = new ArrayList<>();

  public AlertListParser(String json) {
    throwExceptionIfStringIsEmpty(json);
    JSONObject alerts = createJSONObjectThrowExceptionIfInvalid(json);
    attemptAlertParseThrowExceptionOnFailure(alerts);
  }

  public ArrayList<UnadaptedAlert> getParsedAlerts() {
    return parsed;
  }

  private void throwExceptionIfStringIsEmpty(String text) {
    if (stringIsEmpty(text))
      throw new IllegalArgumentException("An empty JSON string was provided");
  }

  private JSONObject createJSONObjectThrowExceptionIfInvalid(String json) {
    JSONObject obj;
    try { obj = new JSONObject(json); }
    catch(JSONException e)
    { throw new IllegalArgumentException("An invalid JSON string was provided"); }
    return obj;
  }

  private void attemptAlertParseThrowExceptionOnFailure(JSONObject alerts) {
    try { parseAlerts(alerts); }
    catch(JSONException e)
    { throw new IllegalArgumentException("An error occurred while parsing the JSON data: " + e.toString()); }
  }

  private void parseAlerts(JSONObject root) throws JSONException {
    JSONArray alerts = getAlertArray(root);
    for (int i = 0; i < alerts.length(); i++) {
      parseAlert(alerts, i);
    }
  }

  private void parseAlert(JSONArray alerts, int i) throws JSONException {
    UnadaptedAlert ua = new UnadaptedAlert();
    parseGeometry(alerts, ua, i);
    parseAlertProperties(getAlertProperties(alerts, i), ua);
    parsed.add(ua);
  }

  private void parseGeometry(JSONArray alerts, UnadaptedAlert ua, int index) throws JSONException {
    if (hasGeometry(alerts, index))
      ua.setPolygon(new GeometryParser(getGeometry(alerts, index)).parseGeometry().get(0));
  }

  private JSONObject getGeometry(JSONArray alerts, int index) throws JSONException {
    return alerts.getJSONObject(index).getJSONObject("geometry");
  }

  private boolean hasGeometry(JSONArray alerts, int i) throws JSONException {
    return !alerts.getJSONObject(i).isNull("geometry");
  }

  private void parseAlertProperties(JSONObject props, UnadaptedAlert ua) throws JSONException {
    AlertPropertyParser propParser = new AlertPropertyParser(props, ua);
    propParser.parseName();
    propParser.parseId();
    propParser.parseType();
    propParser.parseReferences();
    propParser.parseDescription();
    propParser.parseInstruction();
    propParser.parseSender();
    propParser.parseSenderCode();
    propParser.parseSent();
    propParser.parseOnset();
    propParser.parseExpires();
    propParser.parseEnds();
    propParser.parseNwsHeadline();
    propParser.parseSeverity();
    propParser.parseCertainty();
    propParser.parseUrgency();
    propParser.parseZones();
  }

  private boolean stringIsEmpty(String text) {
    return text.equals("");
  }

  private JSONObject getAlertProperties(JSONArray alerts, int i) throws JSONException {
    return alerts.getJSONObject(i).getJSONObject("properties");
  }

  private JSONArray getAlertArray(JSONObject root) throws JSONException {
    return root.getJSONArray("features");
  }
}
