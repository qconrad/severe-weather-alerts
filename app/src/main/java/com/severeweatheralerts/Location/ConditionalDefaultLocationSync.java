package com.severeweatheralerts.Location;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.UserSync.UserSyncWorkScheduler;

public class ConditionalDefaultLocationSync {
  private final Context context;
  private final double lat;
  private final double lon;
  private final LocationsDao locationsDao;

  public ConditionalDefaultLocationSync(Context context, double lat, double lon) {
    this.context = context;
    this.lat = lat;
    this.lon = lon;
    locationsDao = LocationsDao.getInstance(context);
  }

  public synchronized void sync() {
    updateLocationInDao();
    syncIfLocationChanged();
  }

  private void syncIfLocationChanged() {
    if (!isDifferent()) return;
    scheduleSync();
    setLastSync();
  }

  private void setLastSync() {
    locationsDao.setLastDefaultSync(new GCSCoordinate(lat, lon));
  }

  private void scheduleSync() {
    new UserSyncWorkScheduler(context).oneTimeSync();
  }

  private boolean isDifferent() {
    return new CoordinateDifference(locationsDao.getLastDefaultSync(), new GCSCoordinate(lat, lon)).isDifferent(Constants.LOCATION_CHANGE_MARGIN);
  }

  private void updateLocationInDao() {
    locationsDao.updateDefaultLocation(lat, lon);
  }
}
