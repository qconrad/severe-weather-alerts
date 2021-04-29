package com.severeweatheralerts.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.severeweatheralerts.Adapters.BundleAlertAdapter;
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
    fetchAlerts();
  }

  private void fetchAlerts() {
    new FromLocationPointPopulater(locationsDao.getCoordinate(0), this).populate(new PopulateCallback() {
      @Override
      public void complete(ArrayList<Alert> alerts) {
        locationsDao.setAlerts(0, alerts);
        alertsFetched = true;
      }
      @Override
      public void error(String message) { }
    });
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    if (alertsFetched) startActivity(new Intent(NotificationViewer.this, AlertListActivity.class));
    else startActivity(new Intent(NotificationViewer.this, FetchingAlertDataActivity.class));
  }
}
