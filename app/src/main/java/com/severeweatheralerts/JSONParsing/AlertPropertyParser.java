package com.severeweatheralerts.JSONParsing;

import com.severeweatheralerts.Adapters.UnadaptedAlert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AlertPropertyParser {
  JSONObject props;
  UnadaptedAlert ua;

  public AlertPropertyParser(JSONObject props, UnadaptedAlert ua) {
    this.props = props;
    this.ua = ua;
  }

  public void parseName() throws JSONException {
    ua.setName(getProp("event"));
  }

  public void parseId() throws JSONException {
    ua.setId(getProp("id"));
  }

  public void parseDescription() throws JSONException {
    ua.setDescription(getProp("description"));
  }

  public void parseNwsHeadline() throws JSONException {
    if (hasNwsHeadline())
      ua.setNwsHeadline(getNwsHeadline());
  }

  private String getNwsHeadline() throws JSONException {
    return getParameters().getJSONArray("NWSheadline").getString(0);
  }

  private boolean hasNwsHeadline() throws JSONException {
    return getParameters().has("NWSheadline");
  }

  public void parseEnds() throws JSONException {
    String ends = getProp("ends");
    if (notNull(ends)) ua.setEnds(ends);
  }

  public void parseExpires() throws JSONException {
    String expires = getProp("expires");
    if (notNull(expires)) ua.setExpires(expires);
  }

  public void parseOnset() throws JSONException {
    String onset = getProp("onset");
    if (notNull(onset)) ua.setOnset(onset);
  }

  public void parseSent() throws JSONException {
    String sent = getProp("sent");
    if (notNull(sent)) ua.setSent(sent);
  }

  public void parseInstruction() throws JSONException {
    String instruction = getProp("instruction");
    if (notNull(instruction)) ua.setInstruction(instruction);
  }

  public void parseSeverity() throws JSONException {
    String severity = getProp("severity");
    if (notNull(severity)) ua.setSeverity(severity);
  }

  public void parseUrgency() throws JSONException {
    String urgency = getProp("urgency");
    if (notNull(urgency)) ua.setUrgency(urgency);
  }

  public void parseCertainty() throws JSONException {
    String certainty = getProp("certainty");
    if (notNull(certainty)) ua.setCertainty(certainty);
  }

  public void parseType() throws JSONException {
    String type = getProp("messageType");
    if (notNull(type)) ua.setType(type);
  }

  public void parseReferences() throws  JSONException {
    JSONArray references = props.getJSONArray("references");
    for (int i = 0; i < references.length(); i++) {
      String refId = references.getJSONObject(i).getString("identifier");
      ua.addReferenceId(refId);
    }
  }

  private boolean notNull(String expires) {
    return !expires.equals("null");
  }

  private String getProp(String event) throws JSONException {
    return props.getString(event);
  }

  public void parseSender() throws JSONException {
    ua.setSender(getProp("senderName"));
  }

  public void parseSenderCode() throws JSONException {
    ua.setSenderCode(getSenderCode());
  }

  private String getSenderCode() throws JSONException {
    return getParameters().getJSONArray("PIL").getString(0).substring(0, 3);
  }

  private JSONObject getParameters() throws JSONException {
    return props.getJSONObject("parameters");
  }

  public void parseZones() throws JSONException {
    int zoneCount = getZones().length();
    for (int i = 0; i < zoneCount; i++)
      ua.addZoneLink(getZones().getString(i));
  }

  private JSONArray getZones() throws JSONException {
    return props.getJSONArray("affectedZones");
  }
}
