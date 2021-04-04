package com.severeweatheralerts.Activities;

import android.os.Bundle;

import com.severeweatheralerts.BundleAlertAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NotificationViewer extends AlertViewerActivity {
  @Override
  protected void getAlertFromExtras(Bundle bundle) {
    al = new BundleAlertAdapter(bundle).getAlert();
  }
}
