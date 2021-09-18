package com.severeweatheralerts;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.Location.MockDatabase;

public class FileDBs {
  private static LocationsDao locationsDao;

  public static LocationsDao getLocationsDao(Context context) {
    if (!hasLocationsDaoInstance()) locationsDao = new LocationsDao(new MockDatabase());
    return locationsDao;
  }

  public static GCSCoordinate getLastDefaultSync(Context context) {
    return PaperDB.getInstance(context).read("lastDefaultSync", new GCSCoordinate(0.0, 0.0));
  }

  public static void setLastDefaultSync(Context context, GCSCoordinate coordinate) {
    PaperDB.getInstance(context).write("lastDefaultSync", coordinate);
  }

  public static boolean hasLocationsDaoInstance() {
    return locationsDao != null;
  }
}
