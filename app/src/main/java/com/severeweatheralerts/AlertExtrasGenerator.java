package com.severeweatheralerts;

import android.content.Intent;

import com.severeweatheralerts.Alerts.Alert;

public class AlertExtrasGenerator {

  private final Intent resultIntent;
  private final Alert alert;

  public AlertExtrasGenerator(Alert alert, Intent resultIntent) {
    this.resultIntent = resultIntent;
    this.alert = alert;
  }

  public Intent addExtras() {
    return resultIntent.putExtra("name", alert.getName())
            .putExtra("description", alert.getDescription())
            .putExtra("instruction", alert.getInstruction())
            .putExtra("largeHeadline", alert.getLargeHeadline())
            .putExtra("smallHeadline", alert.getSmallHeadline())
            .putExtra("sent", alert.getSentTime().getTime())
            .putExtra("start", alert.getStartTime().getTime())
            .putExtra("ends", alert.getEndTime().getTime())
            .putExtra("type", alert.getType().toString())
            .putExtra("zones", alert.getZones())
            .putExtra("polygons", getPolygonString())
            .putExtra("sender", alert.getSender())
            .putExtra("senderCode", alert.getSenderCode());
  }

  private String getPolygonString() {
    if (alert.getPolygonCount() > 0) {
      StringBuilder polygonString = new StringBuilder();
      for (int p = 0; p < alert.getPolygonCount(); p++) {
        StringBuilder coordinates = new StringBuilder();
        for (int c = 0; c < alert.getPolygon(p).getCoordinateCount(); c++) {
          coordinates.append("[").append(alert.getPolygon(p).getCoordinate(c).getX()).append(", ").append(alert.getPolygon(p).getCoordinate(c).getY()).append("]");
          if (c < alert.getPolygon(p).getCoordinateCount()-1) coordinates.append(",");
        }
        polygonString.append("[").append(coordinates).append("]");
        if (p < alert.getPolygonCount()-1) polygonString.append(",");
      }
      return "[" + polygonString + "]";
    }
    return "[[]]";
  }
}
