package com.severeweatheralerts.Activities;

import android.os.Bundle;

import com.severeweatheralerts.Adapters.BundleAlertAdapter;

import io.paperdb.Paper;

public class NotificationViewer extends AlertViewerActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Paper.init(this);
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void getAlertFromExtras(Bundle bundle) {
    al = new BundleAlertAdapter(bundle).getAlert();
    locationIndex = 0;
  }
}
