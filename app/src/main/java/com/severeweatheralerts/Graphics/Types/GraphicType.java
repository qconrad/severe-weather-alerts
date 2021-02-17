package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.GraphicGeneration.GraphicGenerator;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;

public interface GraphicType {
  String getTitle();
  GraphicGenerator getGenerator(Context context, Alert alert, MercatorCoordinate location);
}
