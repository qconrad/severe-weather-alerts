package com.severeweatheralerts.JSONParsing;

import com.severeweatheralerts.UnadaptedAlert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AlertPropertyParser {
  JSONObject props;
  UnadaptedAlert pa;

  public AlertPropertyParser(JSONObject props, UnadaptedAlert pa) {
    this.props = props;
    this.pa = pa;
  }

  public void parseName() throws JSONException {
    pa.setName(getProp("event"));
  }

  public void parseId() throws JSONException {
    pa.setId(getProp("id"));
  }

  public void parseDescription() throws JSONException {
    pa.setDescription(getProp("description"));
  }

  public void parseNwsHeadline() throws JSONException {
    if (hasNwsHeadline())
      pa.setNwsHeadline(getNwsHeadline());
  }

  private String getNwsHeadline() throws JSONException {
    return props.getJSONObject("parameters").getJSONArray("NWSheadline").getString(0);
  }

  private boolean hasNwsHeadline() throws JSONException {
    return props.getJSONObject("parameters").has("NWSheadline");
  }

  public void parseEnds() throws JSONException {
    String ends = getProp("ends");
    if (notNull(ends)) pa.setEnds(ends);
  }

  public void parseExpires() throws JSONException {
    String expires = getProp("expires");
    if (notNull(expires)) pa.setExpires(expires);
  }

  public void parseOnset() throws JSONException {
    String onset = getProp("onset");
    if (notNull(onset)) pa.setOnset(onset);
  }

  public void parseSent() throws JSONException {
    String sent = getProp("sent");
    if (notNull(sent)) pa.setSent(sent);
  }

  public void parseInstruction() throws JSONException {
    String instruction = getProp("instruction");
    if (notNull(instruction)) pa.setInstruction(instruction);
  }

  public void parseSeverity() throws  JSONException {
    String severity = getProp("severity");
    if (notNull(severity)) pa.setSeverity(severity);
  }

  public void parseType() throws  JSONException {
    String type = getProp("messageType");
    if (notNull(type)) pa.setType(type);
  }

  public void parseReferences() throws  JSONException {
    JSONArray references = props.getJSONArray("references");
    for (int i = 0; i < references.length(); i++) {
      String refId = references.getJSONObject(i).getString("identifier");
      pa.addReferenceId(refId);
    }
  }

  private boolean notNull(String expires) {
    return !expires.equals("null");
  }

  private String getProp(String event) throws JSONException {
    return props.getString(event);
  }

  public void parseSender() throws JSONException {
    pa.setSender(getProp("senderName"));
  }
}
