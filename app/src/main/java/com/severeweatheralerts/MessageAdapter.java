package com.severeweatheralerts;

import com.severeweatheralerts.Adapters.DescriptionHeadlineRemover;
import com.severeweatheralerts.Adapters.HeadlineAdapter;
import com.severeweatheralerts.Adapters.TypeAdapter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.AlertFactory;

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
    alert.setType(new TypeAdapter(message.get("type")).adaptType());
    HeadlineAdapter headlineAdapter = new HeadlineAdapter(message.get("nwsHeadline"), message.get("description"));
    alert.setLargeHeadline(headlineAdapter.getLargeHeadline());
    alert.setSmallHeadline(headlineAdapter.getSmallHeadline());
    return alert;
  }
}
