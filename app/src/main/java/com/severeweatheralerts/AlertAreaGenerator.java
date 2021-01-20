package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;

public class AlertAreaGenerator {
  public AlertAreaGenerator(Alert alert) {}

  public Graphic getGraphic() {
    Graphic graphic = new Graphic();
    graphic.setTitle("Alert Area");
    return graphic;
  }
}
