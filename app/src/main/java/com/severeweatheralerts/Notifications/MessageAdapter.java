package com.severeweatheralerts.Notifications;

import com.severeweatheralerts.Adapters.DescriptionAdapter;
import com.severeweatheralerts.Adapters.DescriptionHeadlineRemover;
import com.severeweatheralerts.Adapters.EndTimeAdapter;
import com.severeweatheralerts.Adapters.ExpectedUpdateTimeAdapter;
import com.severeweatheralerts.Adapters.GeoJSONPolygon;
import com.severeweatheralerts.Adapters.HeadlineAdapter;
import com.severeweatheralerts.Adapters.InstructionAdapter;
import com.severeweatheralerts.Adapters.MotionDescriptionAdapter;
import com.severeweatheralerts.Adapters.PolygonAdapter;
import com.severeweatheralerts.Adapters.SendTimeAdapter;
import com.severeweatheralerts.Adapters.SenderCodeAdapter;
import com.severeweatheralerts.Adapters.StartTimeAdapter;
import com.severeweatheralerts.Adapters.TypeAdapter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.AlertFactory;
import com.severeweatheralerts.JSONParsing.GeometryParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class MessageAdapter {
  private final Map<String, String> message;

  public MessageAdapter(Map<String, String> message) {
    this.message = message;
  }

  public Alert getAlert() {
    Alert alert = new AlertFactory().getAlert(message.get("name"));
    alert.setName(message.get("name"));
    alert.setNwsId(message.get("id"));
    alert.setDescription(new DescriptionHeadlineRemover(new DescriptionAdapter(message.get("description")).adaptDescription()).removeHeadlinesFromDescription());
    alert.setInstruction(new InstructionAdapter(message.get("instruction"), message.get("type")).adaptInstruction());
    alert.setSentTime(new SendTimeAdapter(message.get("sent")).adaptSendTime());
    alert.setStartTime(new StartTimeAdapter(message.get("onset")).adaptStartTime());
    alert.setEndTime(new EndTimeAdapter(message.get("ends"), message.get("expires")).adaptEndTime());
    alert.setExpectedUpdateTime(new ExpectedUpdateTimeAdapter(message.get("ends"), message.get("expires")).adaptUpdateExpectedTime());
    alert.setType(new TypeAdapter(message.get("type"), alert.getSentTime(), alert.getEndTime()).adaptType());
    alert.setSenderCode(new SenderCodeAdapter(message.get("senderCode")).adapterSenderCode());
    alert.setSender(message.get("senderName"));
    HeadlineAdapter headlineAdapter = new HeadlineAdapter(message.get("nwsHeadline"), message.get("description"));
    alert.setLargeHeadline(headlineAdapter.getLargeHeadline());
    alert.setSmallHeadline(headlineAdapter.getSmallHeadline());
    alert.setMotionVector(new MotionDescriptionAdapter(message.get("motionDescription")).getMotionVector());
    if (message.get("polygonType") != null) {
      String geometryString = "{ type: '" + message.get("polygonType") + "', coordinates: " + message.get("polygon") + "}";
      try {
        ArrayList<GeoJSONPolygon> geometry = new GeometryParser(new JSONObject(geometryString)).parseGeometry();
        for (int i = 0; i < geometry.size(); i++)
          alert.addPolygon(PolygonAdapter.toMercatorPolygon(geometry.get(i)));
      } catch (JSONException e) {
        e.printStackTrace();
      }
    } else {
      String zones = message.get("zones");
      if (zones != null) {
        try {
          JSONArray jsonArray = new JSONArray(zones);
          for (int i = 0; i < jsonArray.length(); i++) {
            alert.addZoneLink("https://api.weather.gov/zones/" + jsonArray.getString(i));
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    }
    return alert;
  }
}
