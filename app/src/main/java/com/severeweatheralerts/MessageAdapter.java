package com.severeweatheralerts;

import com.severeweatheralerts.Adapters.DescriptionHeadlineRemover;
import com.severeweatheralerts.Adapters.EndTimeAdapter;
import com.severeweatheralerts.Adapters.HeadlineAdapter;
import com.severeweatheralerts.Adapters.SendTimeAdapter;
import com.severeweatheralerts.Adapters.SenderCodeAdapter;
import com.severeweatheralerts.Adapters.StartTimeAdapter;
import com.severeweatheralerts.Adapters.TypeAdapter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.AlertFactory;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Map;

import static com.severeweatheralerts.TextUtils.TextBeautifier.beautify;

public class MessageAdapter {
  private final Map<String, String> message;

  public MessageAdapter(Map<String, String> message) {
    this.message = message;
  }

  public Alert getAlert() {
    Alert alert = new AlertFactory().getAlert(message.get("name"));
    alert.setName(message.get("name"));
    alert.setDescription(new DescriptionHeadlineRemover(beautify(message.get("description"))).removeHeadlinesFromDescription());
    alert.setInstruction(beautify(message.get("instruction")));
    alert.setSentTime(new SendTimeAdapter(message.get("sent")).adaptSendTime());
    alert.setStartTime(new StartTimeAdapter(message.get("onset")).adaptStartTime());
    alert.setEndTime(new EndTimeAdapter(message.get("ends"), message.get("expires")).adaptEndTime());
    alert.setType(new TypeAdapter(message.get("type")).adaptType());
    alert.setSenderCode(new SenderCodeAdapter(message.get("senderCode")).adapterSenderCode());
    alert.setSender(message.get("senderName"));
    HeadlineAdapter headlineAdapter = new HeadlineAdapter(message.get("nwsHeadline"), message.get("description"));
    alert.setLargeHeadline(headlineAdapter.getLargeHeadline());
    alert.setSmallHeadline(headlineAdapter.getSmallHeadline());
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
    return alert;
  }
}
