package com.severeweatheralerts.Adapters;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.AlertFactory;

import java.util.ArrayList;

public class AlertAdapter {
  ArrayList<Alert> alerts = new ArrayList<>();
  ArrayList<UnadaptedAlert> unadaptedAlerts;

  public AlertAdapter(ArrayList<UnadaptedAlert> unadaptedAlerts) {
    this.unadaptedAlerts = unadaptedAlerts;
    adaptAlerts();
    checkForDiscontinuedAlerts();
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

  private void checkForDiscontinuedAlerts() {
    alerts = new DiscontinuationChecker(unadaptedAlerts, alerts).getAlerts();
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
    adaptCertainty(ua, al);
    adaptUrgency(ua, al);
    adaptSender(ua, al);
    adaptSenderCode(ua, al);
    adaptId(ua, al);
    adaptType(ua, al);
    adaptSendTime(ua, al);
    adaptExpectedUpdateTime(ua, al);
    adaptStartTime(ua, al);
    adaptEndTime(ua, al);
    adaptAlertArea(ua, al);
    adaptMotionVector(ua, al);
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
    HeadlineAdapter headlineAdapter = new HeadlineAdapter(ua.getNwsHeadline(), ua.getDescription());
    al.setLargeHeadline(headlineAdapter.getLargeHeadline());
    al.setSmallHeadline(headlineAdapter.getSmallHeadline());
  }

  private void removeHeadlinesFromDescription(Alert al) {
    al.setDescription(new DescriptionHeadlineRemover(al.getDescription()).removeHeadlinesFromDescription());
  }

  private void adaptEndTime(UnadaptedAlert ua, Alert al) {
    al.setEndTime(new EndTimeAdapter(ua.getEnds(), ua.getExpires()).adaptEndTime());
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
    al.setDescription(new DescriptionAdapter(ua.getDescription()).adaptDescription());
  }

  private void adaptSeverity(UnadaptedAlert ua, Alert al) {
    al.setSeverity(new SeverityAdapter(ua.getSeverity()).adaptSeverity());
  }

  private void adaptCertainty(UnadaptedAlert ua, Alert al) {
    al.setCertainty(new CertaintyAdapter(ua.getCertainty()).adaptSeverity());
  }

  private void adaptUrgency(UnadaptedAlert ua, Alert al) {
    al.setUrgency(new UrgencyAdapter(ua.getUrgency()).adaptSeverity());
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
    al.setSenderCode(new SenderCodeAdapter(ua.getSenderCode()).adapterSenderCode());
  }

  private void adaptId(UnadaptedAlert ua, Alert al) {
    al.setNwsId(ua.getId());
  }


  private void adaptType(UnadaptedAlert ua, Alert al) {
    al.setType(new TypeAdapter(ua.getType()).adaptType());
  }

  private void adaptMotionVector(UnadaptedAlert ua, Alert al) {
    if (ua.getMotionDescription() == null) return;
    al.setMotionVector(new MotionDescriptionAdapter(ua.getMotionDescription()).getMotionVector());
  }

  public ArrayList<Alert> getAdaptedAlerts() {
    return alerts;
  }
}
