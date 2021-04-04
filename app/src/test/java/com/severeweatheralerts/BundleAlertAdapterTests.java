package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BundleAlertAdapterTests {
  @Test
  public void returnsAlert() {
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(new HashMap<>());
    assertNotNull(bundleAlertAdapter.getAlert());
  }

  @Test
  public void returnsName() {
    Map<String, String> map = new HashMap<>();
    map.put("name", "Tornado Warning");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(map);
    assertEquals("Tornado Warning", bundleAlertAdapter.getAlert().getName());
  }

  @Test
  public void returnsDifferentName() {
    Map<String, String> map = new HashMap<>();
    map.put("name", "Tornado Watch");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(map);
    assertEquals("Tornado Watch", bundleAlertAdapter.getAlert().getName());
  }
}
