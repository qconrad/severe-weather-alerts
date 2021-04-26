package com.severeweatheralerts.Activities;

import android.os.Bundle;

import com.severeweatheralerts.Adapters.BundleAlertAdapter;

public class NotificationViewer extends AlertViewerActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void getAlertFromExtras(Bundle bundle) {
    al = new BundleAlertAdapter(bundle).getAlert();
    locationIndex = 0;
  }
}
