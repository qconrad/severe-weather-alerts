package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;

public interface GraphicType {
  String getTitle();
  int getValidDuration();
  GraphicGenerator getGenerator(Context context, Alert alert, GCSCoordinate location);
}
