package com.severeweatheralerts;

import android.os.Bundle;

public class ReferenceViewerActivity extends AlertViewerActivity {
  @Override
  protected void getAlertFromExtras(Bundle bundle) {
    if (bundle != null) {
      int locIndex = bundle.getInt("locIndex");
      String alertIndex = bundle.getString("alertID");
      al = LocationsDao.getLocationList().get(0).getAlerts().get(0);
    }
  }
}
