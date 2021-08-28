package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.URL;

public class HurricaneTornadoThreatGenerator extends TropicalGraphicGenerator {
  public HurricaneTornadoThreatGenerator(Context context, Alert alert, GCSCoordinate location) {
    super(context, alert, location, "tctornado");
  }

  @Override
  protected Layer getLayer(Bounds bounds, String dateString) {
    return new Layer(new URL().getHurricaneTornadoThreat(bounds, getRegion(), dateString));
  }
}
