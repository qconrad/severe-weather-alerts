package com.severeweatheralerts.Activities;

import android.os.Bundle;

import com.severeweatheralerts.BundleAlertAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NotificationViewer extends AlertViewerActivity {
  @Override
  protected void getAlertFromExtras(Bundle bundle) {
    al = new BundleAlertAdapter(bundleToMap(bundle)).getAlert();
  }

  private Map<String, String> bundleToMap(Bundle extras) {
    Map<String, String> map = new HashMap<>();
    Set<String> ks = extras.keySet();
    for (String key : ks) map.put(key, extras.getString(key));
    return map;
  }

}
