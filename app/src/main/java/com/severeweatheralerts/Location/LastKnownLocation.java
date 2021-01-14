package com.severeweatheralerts.Location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import static com.severeweatheralerts.PermissionManager.hasLocationPermissions;


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
    Location location = null;
    if (hasLocationPermissions(context))
      location = locationManager.getLastKnownLocation(getProvider(locationManager, getCriteria()));
    return location;
  }
  private void getLocationService() {
    locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
  }

  private String getProvider(LocationManager lm, Criteria criteria) {
    return lm.getBestProvider(criteria, true);
  }

  private Criteria getCriteria() {
    Criteria criteria = new Criteria();
    criteria.setAccuracy(Criteria.NO_REQUIREMENT);
    return criteria;
  }
}
