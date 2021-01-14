package com.severeweatheralerts.Location;

import android.location.Location;

public interface LocationUpdateCallback {
  void onUpdate(Location location);
}
