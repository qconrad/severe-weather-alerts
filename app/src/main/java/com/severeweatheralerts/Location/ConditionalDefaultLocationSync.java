package com.severeweatheralerts.Location;

import static com.severeweatheralerts.FileDBs.locationsDao;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.UserSync.UserSyncWorkScheduler;

public class ConditionalDefaultLocationSync {
  private final Context context;
  private final double lat;
  private final double lon;

  public ConditionalDefaultLocationSync(Context context, double lat, double lon) {
    this.context = context;
    this.lat = lat;
    this.lon = lon;
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
    locationsDao.setDefaultLocation(locationsDao.getDefaultLocation().setCoordinate(new GCSCoordinate(lat, lon)));
  }
}
