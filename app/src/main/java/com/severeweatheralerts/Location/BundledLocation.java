package com.severeweatheralerts.Location;

import static com.severeweatheralerts.FileDB.getLocationsDao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.preference.PreferenceManager;

import com.severeweatheralerts.Adapters.GCSCoordinate;

public class BundledLocation {
  private final Context context;
  private final Intent data;

  public BundledLocation(Context context, Intent data) {
    this.context = context;
    this.data = data;
  }

  public void setFixedLocation() {
    Bundle extras = data.getExtras();
    getLocationsDao(context).setDefaultLocation(getLocationsDao(context).getDefaultLocation()
            .setName(extras.getString("name"))
            .setCoordinate(new GCSCoordinate(extras.getDouble("lat"), extras.getDouble("lon"))));
    PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("usefixed", true).apply();
    new ConditionalDefaultLocationSync(context, extras.getDouble("lat"), extras.getDouble("lon")).sync();
  }
}
