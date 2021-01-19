package com.severeweatheralerts.Location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

public class LastKnownLocation extends LocationGetter {
  private LocationManager locationManager;

  public LastKnownLocation(Context context) {
    super(context);
  }

  @SuppressLint("MissingPermission")
  public Location getLocation() {
    getLocationService();
    return getLastKnownLocation();
  }

  @SuppressLint("MissingPermission")
  private Location getLastKnownLocation() {
    if (getProvider(locationManager, getCriteria()) == null) return null;
    return locationManager.getLastKnownLocation(getProvider(locationManager, getCriteria()));
  }
  protected void getLocationService() {
    locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
  }

  private String getProvider(LocationManager lm, Criteria criteria) {
    return lm.getBestProvider(criteria, true);
  }

  protected Criteria getCriteria() {
    return new Criteria();
  }
}
