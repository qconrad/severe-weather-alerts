package com.severeweatheralerts;

import org.json.JSONException;
import org.json.JSONObject;

public class FeedbackPayloadGenerator {
  private final String message;
  private final String type;

  public FeedbackPayloadGenerator(String message, String type) {
    this.message = message;
    this.type = type;
  }

  public String getJSONString() {
    JSONObject jsonObject = new JSONObject();
    try { addFields(jsonObject); }
    catch (JSONException e) { e.printStackTrace(); }
    return jsonObject.toString();
  }

  private void addFields(JSONObject jsonObject) throws JSONException {
    jsonObject.put("message", message);
    jsonObject.put("type", type);
  }
}
