package com.severeweatheralerts.Graphics.Types;

import android.content.Context;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.GraphicGeneration.GraphicGenerator;
import com.severeweatheralerts.Location.Location;

public interface GraphicType {
  String getTitle();
  GraphicGenerator getGenerator(Context context, Alert alert, Location location);
}
