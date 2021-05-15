package com.severeweatheralerts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.preference.PreferenceManager;

import com.severeweatheralerts.Location.ConditionalDefaultLocationSync;
import com.severeweatheralerts.Location.LocationsDao;

public class BundledLocation {
  private final Context context;
  private final Intent data;

  public BundledLocation(Context context, Intent data) {
    this.context = context;
    this.data = data;
  }

  public void setFixedLocation() {
    Bundle extras = data.getExtras();
    LocationsDao.getInstance(context).setDefaultLocation(extras.getString("name"), extras.getDouble("lat"), extras.getDouble("lon"));
    PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("usefixed", true).putBoolean("first_run", false).apply();
    new ConditionalDefaultLocationSync(context, extras.getDouble("lat"), extras.getDouble("lon")).sync();
  }
}
