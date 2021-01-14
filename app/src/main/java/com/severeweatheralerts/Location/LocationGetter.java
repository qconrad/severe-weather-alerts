package com.severeweatheralerts.Location;

import android.content.Context;

public abstract class LocationGetter {
  protected final Context context;

  public LocationGetter(Context context) {
    this.context = context;
  }
}
