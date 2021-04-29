package com.severeweatheralerts.Adapters;

import android.os.Bundle;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.AlertFactory;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.Polygon.Polygon;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Date;

public class BundleAlertAdapter {
  private final Bundle bundle;

  public BundleAlertAdapter(Bundle bundle) {
    this.bundle = bundle;
  }

  public Alert getAlert() {
    Alert alert = new AlertFactory().getAlert(bundle.getString("name"));
    alert.setName(bundle.getString("name"));
    alert.setNwsId(bundle.getString("id"));
    alert.setDescription(bundle.getString("description"));
    alert.setInstruction(bundle.getString("instruction"));
    alert.setLargeHeadline(bundle.getString("largeHeadline"));
    alert.setSmallHeadline(bundle.getString("smallHeadline"));
    alert.setSentTime(new Date(bundle.getLong("sent")));
    alert.setStartTime(new Date(bundle.getLong("start")));
    alert.setEndTime(new Date(bundle.getLong("ends")));
    long expectedUpdate = bundle.getLong("expectedUpdate");
    if (expectedUpdate != 0) alert.setExpectedUpdateTime(new Date(expectedUpdate));
    adaptPolygons(alert);
    String type = bundle.getString("type");;
    if (type != null) alert.setType(Alert.Type.valueOf(type));
    alert.setSenderCode(bundle.getString("senderCode"));
    alert.setSender(bundle.getString("sender"));
    alert.setZoneLinks(bundle.getStringArrayList("zones"));
    return alert;
  }

  private void adaptPolygons(Alert alert) {
    if (bundle.getString("polygons") != null) {
      try {
        JSONArray jsonArray = new JSONArray(bundle.getString("polygons"));
        for (int p = 0; p < jsonArray.length(); p++) {
          Polygon polygon = new Polygon();
          for (int c = 0; c < jsonArray.getJSONArray(p).length(); c++) {
            polygon.addCoordinate(new MercatorCoordinate(jsonArray.getJSONArray(p).getJSONArray(c).getDouble(0), jsonArray.getJSONArray(p).getJSONArray(c).getDouble(1)));
          }
          alert.addPolygon(polygon);
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
  }
}
