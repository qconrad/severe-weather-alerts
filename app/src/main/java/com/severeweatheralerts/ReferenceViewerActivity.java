package com.severeweatheralerts;

import android.os.Bundle;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.AlertListSearcher;

import java.util.ArrayList;

public class ReferenceViewerActivity extends AlertViewerActivity {
  @Override
  protected void getAlertFromExtras(Bundle bundle) {
    if (bundle != null) {
      int locIndex = bundle.getInt("locIndex");
      String alertIndex = bundle.getString("alertID");
      al = new AlertListSearcher(getAlerts(locIndex)).findAlertByID(alertIndex);
    }
  }

  private ArrayList<Alert> getAlerts(int locIndex) {
    return getLocation(locIndex).getAlerts();
  }

  private Location getLocation(int locIndex) {
    return LocationsDao.getLocationList().get(locIndex);
  }
}
