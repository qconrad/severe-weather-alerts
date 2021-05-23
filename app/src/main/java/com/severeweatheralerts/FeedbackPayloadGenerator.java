package com.severeweatheralerts;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

public class FeedbackPayloadGenerator {
  private final String message;
  private final String type;
  private final Context context;
  private final String token;

  public FeedbackPayloadGenerator(Context context, String message, String type, String token) {
    this.context = context;
    this.message = message;
    this.type = type;
    this.token = token;
  }

  public String getJSONString() {
    JSONObject jsonObject = new JSONObject();
    try { addFields(jsonObject); }
    catch (JSONException e) { e.printStackTrace(); }
    catch (Exception e) { e.printStackTrace(); }
    return jsonObject.toString();
  }

  private void addFields(JSONObject payload) throws JSONException {
    payload.put("token", token);
    payload.put("message", message);
    payload.put("type", type);
    payload.put("androidVersion", Build.VERSION.SDK_INT);
    payload.put("deviceManufacturer", Build.MANUFACTURER);
    payload.put("deviceModel", Build.MODEL);
    payload.put("deviceProduct", Build.PRODUCT);
    payload.put("usingFixedLocation", isUsingFixed());
    addAppVersion(payload);
  }

  private void addAppVersion(JSONObject payload) throws JSONException {
    try { payload.put("appVersion", getAppVersion()); }
    catch (PackageManager.NameNotFoundException e) { e.printStackTrace(); }
  }

  private boolean isUsingFixed() {
    return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("usefixed", false);
  }

  private int getAppVersion() throws PackageManager.NameNotFoundException {
    return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
  }
}
