package com.severeweatheralerts.Activities;

import android.os.Bundle;

import com.severeweatheralerts.BundleAlertAdapter;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Location.LocationsDao;

public class NotificationViewer extends AlertViewerActivity {
  @Override
  protected void getAlertFromExtras(Bundle bundle) {
    al = new BundleAlertAdapter(bundle).getAlert();
    LocationsDao.addLocation(new Location());
    locationIndex = 0;
  }
}
