package com.severeweatheralerts.Adapters;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.AlertFactory;
import com.severeweatheralerts.Graphics.MercatorCoordinateAdapter;
import com.severeweatheralerts.Graphics.Polygon;

import java.util.ArrayList;

import static com.severeweatheralerts.TextUtils.TextBeautifier.beautify;

public class AlertAdapter {
  ArrayList<Alert> alerts = new ArrayList<>();
  ArrayList<UnadaptedAlert> unadaptedAlerts;

  public AlertAdapter(ArrayList<UnadaptedAlert> unadaptedAlerts) {
    this.unadaptedAlerts = unadaptedAlerts;
    adaptAlerts();
    linkReferences();
  }

  private void adaptAlerts() {
    for (int i = 0; i < unadaptedAlerts.size(); i++) {
      Alert al = generateObject(unadaptedAlerts.get(i).getName());
      adaptAlert(unadaptedAlerts.get(i), al);
      alerts.add(al);
    }
  }

  private void linkReferences() {
    alerts = new ReferenceLinker(unadaptedAlerts, alerts).linkReferences();
  }

  private Alert generateObject(String name) {
    return new AlertFactory().getAlert(name);
  }

  private void adaptAlert(UnadaptedAlert ua, Alert al) {
    adaptName(ua, al);
    adaptHeadlines(ua, al);
    adaptDescription(ua, al);
    adaptInstruction(ua, al);
    adaptSeverity(ua, al);
    adaptSender(ua, al);
    adaptSenderCode(ua, al);
    adaptId(ua, al);
    adaptType(ua, al);
    adaptSendTime(ua, al);
    adaptExpectedUpdateTime(ua, al);
    adaptStartTime(ua, al);
    adaptEndTime(ua, al);
    adaptAlertArea(ua, al);
    removeHeadlinesFromDescription(al); // Requires already parsed description
  }

  private void adaptAlertArea(UnadaptedAlert ua, Alert al) {
    if (ua.hasGeometry()) al.addPolygon(PolygonAdapter.toMercatorPolygon(ua.getPolygon()));
    else {
      for (int i = 0; i < ua.getZoneLinkCount(); i++)
        al.addZoneLink(ua.getZoneLink(i));
    }
  }

  private void adaptHeadlines(UnadaptedAlert ua, Alert al) {
    adaptNwsHeadline(ua, al);
    adaptDescriptionHeadline(ua, al);
  }

  private void adaptDescriptionHeadline(UnadaptedAlert ua, Alert al) {
    String descHeadline = new DescriptionHeadlineAdapter(ua.getDescription()).adaptDescriptionHeadline();
    if (descHeadline != null) al.setLargeHeadline(descHeadline);
  }

  private void adaptNwsHeadline(UnadaptedAlert ua, Alert al) {
    NwsHeadlineAdapter nwsHa = new NwsHeadlineAdapter(ua.getNwsHeadline());
    al.setLargeHeadline(nwsHa.adaptNwsHeadlineLarge());
    al.setSmallHeadline(nwsHa.adaptNwsHeadlineSmall());
  }

  private void removeHeadlinesFromDescription(Alert al) {
    al.setDescription(new DescriptionHeadlineRemover(al.getDescription()).removeHeadlinesFromDescription());
  }

  private void adaptEndTime(UnadaptedAlert ua, Alert al) {
    al.setEndTime(new EndTimeAdapater(ua.getEnds(), ua.getExpires()).adaptEndTime());
  }

  private void adaptStartTime(UnadaptedAlert ua, Alert al) {
    al.setStartTime(new StartTimeAdapter(ua.getOnset()).adaptStartTime());
  }

  private void adaptExpectedUpdateTime(UnadaptedAlert ua, Alert al) {
    al.setExpectedUpdateTime(new ExpectedUpdateTimeAdapter(ua.getEnds(), ua.getExpires()).adaptUpdateExpectedTime());
  }

  private void adaptSendTime(UnadaptedAlert ua, Alert al) {
    al.setSentTime(new SendTimeAdapter(ua.getSent()).adaptSendTime());
  }

  private void adaptDescription(UnadaptedAlert ua, Alert al) {
    if (notNullOrEmpty(ua.getDescription())) al.setDescription(beautify(ua.getDescription()));
  }

  private boolean notNullOrEmpty(String text) {
    return text != null && !text.equals("");
  }

  private void adaptSeverity(UnadaptedAlert ua, Alert al) {
    al.setSeverity(new SeverityAdapter(ua.getSeverity()).adaptSeverity());
  }

  private void adaptInstruction(UnadaptedAlert ua, Alert al) {
    al.setInstruction(new InstructionAdapter(ua.getInstruction(), ua.getType()).adaptInstruction());
  }

  private void adaptName(UnadaptedAlert ua, Alert al) {
    al.setName(ua.getName());
  }

  private void adaptSender(UnadaptedAlert ua, Alert al) {
    al.setSender(ua.getSender());
  }

  private void adaptSenderCode(UnadaptedAlert ua, Alert al) {
    al.setSenderCode(ua.getSenderCode());
  }

  private void adaptId(UnadaptedAlert ua, Alert al) {
    al.setNwsId(ua.getId());
  }


  private void adaptType(UnadaptedAlert ua, Alert al) {
    al.setType(new TypeAdapter(ua.getType()).adaptType());
  }

  public ArrayList<Alert> getAdaptedAlerts() {
    return alerts;
  }
}
