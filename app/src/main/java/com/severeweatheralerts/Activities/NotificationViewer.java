package com.severeweatheralerts.Activities;

import static com.severeweatheralerts.FileDB.getLocationsDao;

import android.content.Intent;
import android.os.Bundle;

import com.severeweatheralerts.Adapters.BundleAlertAdapter;
import com.severeweatheralerts.AlertListTools.AlertFinder;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Networking.LocationPopulaters.FromLocationPointPopulater;
import com.severeweatheralerts.Networking.LocationPopulaters.PopulateCallback;

import java.util.ArrayList;

public class NotificationViewer extends AlertViewerActivity {
  private boolean alertsFetched = false;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void getAlertFromExtras(Bundle bundle) {
    al = new BundleAlertAdapter(bundle).getAlert();
    locationIndex = 0;
    location = getLocationsDao(this).getLocation(locationIndex);
    fetchAlerts();
  }

  private void fetchAlerts() {
    new FromLocationPointPopulater(location.getCoordinate(), this).populate(new PopulateCallback() {
      @Override
      public void complete(ArrayList<Alert> alerts) {
        location.setAlerts(alerts);
        alertsFetched = true;
        fillMissingData(new AlertFinder(alerts).findAlertByID(al.getNwsId()));
      }
      @Override
      public void error(String message) { }
    });
  }

  private void fillMissingData(Alert alert) {
    if (alert == null) return;
    al = alert;
    populateReferences();
  }

  @Override
  public void onBackPressed() {
    if (alertsFetched) startActivity(new Intent(NotificationViewer.this, AlertListActivity.class));
    else startActivity(new Intent(NotificationViewer.this, GettingLatestDataActivity.class));
  }
}
